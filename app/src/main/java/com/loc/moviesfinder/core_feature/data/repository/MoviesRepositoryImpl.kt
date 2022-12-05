package com.loc.moviesfinder.core_feature.data.repository

import android.util.Log
import com.loc.moviesfinder.core_feature.data.local.MoviesDao
import com.loc.moviesfinder.core_feature.data.mapper.*
import com.loc.moviesfinder.core_feature.data.remote.MoviesApi
import com.loc.moviesfinder.core_feature.data.remote.dao.MovieDetailsResponse
import com.loc.moviesfinder.core_feature.data.remote.dao.MoviesCollectionResponse
import com.loc.moviesfinder.core_feature.data.remote.dao.SearchResult
import com.loc.moviesfinder.core_feature.domain.model.*
import com.loc.moviesfinder.core_feature.domain.util.MoviesGenre
import com.loc.moviesfinder.core_feature.domain.util.Resource
import com.loc.moviesfinder.core_feature.domain.repository.MoviesRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.Response

private val TAG = "MoviesRepositoryImpl"

class MoviesRepositoryImpl(
    private val moviesApi: MoviesApi,
    private val moviesDao: MoviesDao,
) : MoviesRepository {

    override suspend fun getTrendingMovies(page: Int): Response<MoviesCollectionResponse> {
        return moviesApi.getTrendingMovies(page = page)
    }

    override suspend fun getMoviesList(
        moviesGenre: MoviesGenre,
        page: Int,
    ): Resource<List<Movie>> {
        return try {
            if (moviesGenre == MoviesGenre.LATEST) {
                val response = moviesApi.getLatestMovies(page = page)
                handleMoviesCollectionResponse(response)
            } else if (moviesGenre == MoviesGenre.NOW_PLAYING) {
                val response = moviesApi.getNowPlayingMovies(page = page)
                handleMoviesCollectionResponse(response)
            } else if (moviesGenre == MoviesGenre.TOP_RATED) {
                val response = moviesApi.getTopRatedMovies(page = page)
                handleMoviesCollectionResponse(response)
            } else if (moviesGenre == MoviesGenre.UPCOMING) {
                val response = moviesApi.getUpComingMovies(page = page)
                handleMoviesCollectionResponse(response)
            } else if (moviesGenre == MoviesGenre.POPULAR) {
                val response = moviesApi.getPopularMovies(page = page)
                handleMoviesCollectionResponse(response)
            } else {
                throw Exception("Select a valid MoviesGenre")
            }

        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    private fun handleMoviesCollectionResponse(response: Response<MoviesCollectionResponse>): Resource<List<Movie>> {
        return if (response.body() != null) {
            val movies = response.body()!!.results.map { it.toMovie() }
            Resource.Success(movies)
        } else {
            val errorDescription = getResponseError(response.code())
            Resource.Error(Exception(errorDescription))
        }
    }

    private fun getResponseError(responseCode: Int): String {
        val strResponseCode = responseCode.toString()
        return when (strResponseCode.drop(0)) {
            "4" -> "Please check your internet"
            "5" -> "Server error"
            else -> "Unknown error"
        }
    }

    override suspend fun searchMovies(
        searchQuery: String,
        page: Int,
    ): Resource<List<SearchedMovie>> {
        val handler = CoroutineExceptionHandler { _, throwable ->
            Log.e(TAG, "Exception in searchMovies $throwable")
        }
        val searchedMovies = mutableListOf<SearchedMovie>()
        var parentJob: Job
        withContext(Dispatchers.IO) {
            parentJob = launch(handler) {
                val moviesIds = async {
                    getSearchedMoviesIds(searchQuery, page)
                }.await()
                supervisorScope {
                    async {
                        moviesIds.forEach {
                            launch {
                                val movieDetails = getMovieDetails(it).toMovieDetails()
                                searchedMovies.add(SearchedMovie(it.id, movieDetails))
                            }
                        }
                    }.await()
                }
            }
        }
        var throwable: Throwable? = null
        parentJob.invokeOnCompletion { throwable = it }
        return throwable?.let {
            Resource.Error(Exception(it))
        } ?: Resource.Success(searchedMovies)
    }

    private suspend fun getMovieDetails(it: SearchResult): MovieDetailsResponse {
        val response = moviesApi.getMovieDetails(it.id)
        val body = response.body()
        if (!response.isSuccessful || body == null)
            throw CancellationException(getResponseError(response.code()))
        return body
    }

    private suspend fun getSearchedMoviesIds(
        searchQuery: String,
        page: Int,
    ): List<SearchResult> {
        val response = moviesApi.searchMovies(query = searchQuery, page = page)
        val body = response.body()
        if (!response.isSuccessful || body == null)
            throw Exception(getResponseError(response.code()))
        return body.results
    }

    override suspend fun getMovieDetailsFromNetwork(movieId: Int): Resource<MovieDetails> {
        return try {
            val response = moviesApi.getMovieDetails(movieId)
            if (response.body() != null)
                Resource.Success(response.body()!!.toMovieDetails())
            else
                Resource.Error(Exception(getResponseError(response.code())))
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }

    override suspend fun getMovieReviews(movieId: Int, page: Int): Resource<List<Review>> {
        return try {
            val response = moviesApi.getMovieReviews(movieId = movieId, page = page)
            if (response.body() != null) {
                val reviews = response.body()!!.results.map { it.toReview() }
                Resource.Success(reviews)
            } else {
                throw Exception(getResponseError(response.code()))
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun getMovieCast(movieId: Int): Resource<List<Cast>> {
        return try {
            val response = moviesApi.getMovieCast(movieId)
            if (response.body() != null) {
                val cast = response.body()!!.cast.map { it.toCast() }
                Resource.Success(cast)
            } else {
                throw Exception(getResponseError(response.code()))
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun getMovieFromDatabase(id: Int): MovieDetails? {
        val movieEntity = moviesDao.getMovieById(id)?.toMovieDetails()
        return movieEntity
    }

    override suspend fun inertMovie(movie: MovieDetails) {
        moviesDao.insertMovie(movie.toMovieEntity())
    }

    override suspend fun deleteMovie(id: Int) {
        moviesDao.deleteMovieById(id)
    }
}
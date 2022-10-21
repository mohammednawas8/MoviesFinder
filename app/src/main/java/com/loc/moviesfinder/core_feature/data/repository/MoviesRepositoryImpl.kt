package com.loc.moviesfinder.core_feature.data.repository

import com.loc.moviesfinder.core_feature.data.mapper.toMovie
import com.loc.moviesfinder.core_feature.data.remote.MoviesApi
import com.loc.moviesfinder.core_feature.data.remote.dao.MoviesCollectionResponse
import com.loc.moviesfinder.core_feature.data.util.MoviesGenre
import com.loc.moviesfinder.core_feature.data.util.Resource
import com.loc.moviesfinder.core_feature.domain.model.Movie
import com.loc.moviesfinder.core_feature.domain.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.lang.Exception

class MoviesRepositoryImpl(
    private val moviesApi: MoviesApi,
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
}
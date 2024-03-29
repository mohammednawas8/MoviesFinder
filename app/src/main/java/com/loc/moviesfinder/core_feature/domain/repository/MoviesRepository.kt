package com.loc.moviesfinder.core_feature.domain.repository

import com.loc.moviesfinder.core_feature.data.remote.dao.MoviesCollectionResponse
import com.loc.moviesfinder.core_feature.domain.model.*
import com.loc.moviesfinder.core_feature.domain.util.MoviesGenre
import com.loc.moviesfinder.core_feature.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface MoviesRepository {

    suspend fun getTrendingMovies(page: Int): Response<MoviesCollectionResponse>

    suspend fun getMoviesList(moviesGenre: MoviesGenre, page: Int): Resource<List<Movie>>

    suspend fun searchMovies(searchQuery: String, page: Int): Resource<List<MovieDetails>>

    suspend fun getMovieDetailsFromNetwork(movieId: Int): Resource<MovieDetails>

    suspend fun getMovieReviews(movieId: Int, page: Int): Resource<List<Review>>

    suspend fun getMovieCast(movieId: Int): Resource<List<Cast>>

    suspend fun getMovieFromDatabase(id: Int): MovieDetails?

    suspend fun inertMovie(movie: MovieDetails)

    suspend fun deleteMovie(id: Int)

    fun getSavedMovies(): Flow<List<MovieDetails>>
}
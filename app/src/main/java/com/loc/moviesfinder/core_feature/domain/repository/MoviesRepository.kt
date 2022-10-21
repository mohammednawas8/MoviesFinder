package com.loc.moviesfinder.core_feature.domain.repository

import com.loc.moviesfinder.core_feature.data.remote.dao.MoviesCollectionResponse
import com.loc.moviesfinder.core_feature.data.util.MoviesGenre
import com.loc.moviesfinder.core_feature.data.util.Resource
import com.loc.moviesfinder.core_feature.domain.model.Movie
import retrofit2.Response

interface MoviesRepository {

    suspend fun getTrendingMovies(page: Int): Response<MoviesCollectionResponse>

    suspend fun getMoviesList(moviesGenre: MoviesGenre, page: Int): Resource<List<Movie>>

}
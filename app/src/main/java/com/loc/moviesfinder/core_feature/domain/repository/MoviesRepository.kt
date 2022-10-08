package com.loc.moviesfinder.core_feature.domain.repository

import com.loc.moviesfinder.core_feature.data.remote.dao.MoviesCollectionResponse
import com.loc.moviesfinder.core_feature.data.util.MoviesGenre
import retrofit2.Response

interface MoviesRepository {

    suspend fun getMoviesList(moviesGenre: MoviesGenre, page: Int): Response<MoviesCollectionResponse>

}
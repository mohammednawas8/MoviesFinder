package com.loc.moviesfinder.core_feature.data.repository

import com.loc.moviesfinder.core_feature.data.remote.MoviesApi
import com.loc.moviesfinder.core_feature.data.remote.dao.MoviesCollectionResponse
import com.loc.moviesfinder.core_feature.data.util.MoviesGenre
import com.loc.moviesfinder.core_feature.domain.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class MoviesRepositoryImpl(
    private val moviesApi: MoviesApi,
) : MoviesRepository {

    override suspend fun getMoviesList(
        moviesGenre: MoviesGenre,
        page: Int,
    ): Response<MoviesCollectionResponse> {
        return withContext(Dispatchers.IO) {
            if (moviesGenre == MoviesGenre.LATEST)
                moviesApi.getLatestMovies(page = page)

            if (moviesGenre == MoviesGenre.NOW_PLAYING)
                moviesApi.getNowPlayingMovies(page = page)

            if (moviesGenre == MoviesGenre.POPULAR)
                moviesApi.getPopularMovies(page = page)

            if (moviesGenre == MoviesGenre.UPCOMING)
                moviesApi.getUpComingMovies(page = page)

            if (moviesGenre == MoviesGenre.TOP_RATED)
                moviesApi.getTopRatedMovies(page = page)
            else moviesApi.getTrendingMovies(page = page)
        }
    }
}
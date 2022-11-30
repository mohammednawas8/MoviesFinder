package com.loc.moviesfinder.core_feature.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.loc.moviesfinder.core_feature.domain.model.Review
import com.loc.moviesfinder.core_feature.domain.repository.MoviesRepository
import com.loc.moviesfinder.core_feature.domain.util.Resource

private const val DEFAULT_REVIEWS_KEY = 1

class ReviewsPagingSource(
    private val movieId: Int,
    private val moviesRepository: MoviesRepository,
) : PagingSource<Int, Review>() {

    override fun getRefreshKey(state: PagingState<Int, Review>): Int? {
        return DEFAULT_REVIEWS_KEY
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Review> {
        val key = params.key ?: DEFAULT_REVIEWS_KEY

        val reviews = moviesRepository.getMovieReviews(movieId, key)
        return when (reviews) {
            is Resource.Success -> {
                if (reviews.data != null)
                    LoadResult.Page(
                        reviews.data, prevKey = key - 1, nextKey = key + 1
                    )
                else
                    LoadResult.Error(Throwable("Unknown exception"))

            }
            is Resource.Error -> {
                LoadResult.Error(Throwable(reviews.exception))
            }
            else -> {
                LoadResult.Error(Throwable(""))
            }
        }
    }
}
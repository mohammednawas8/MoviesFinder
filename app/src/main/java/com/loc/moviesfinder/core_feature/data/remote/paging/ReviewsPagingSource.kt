package com.loc.moviesfinder.core_feature.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.loc.moviesfinder.core_feature.domain.model.Review
import com.loc.moviesfinder.core_feature.domain.repository.MoviesRepository
import com.loc.moviesfinder.core_feature.domain.util.Resource

private const val DEFAULT_REVIEWS_KEY = 1

class ReviewsPagingSource(
    private val movieId: Int?,
    private val moviesRepository: MoviesRepository,
) : PagingSource<Int, Review>() {

    override fun getRefreshKey(state: PagingState<Int, Review>): Int? {
        return DEFAULT_REVIEWS_KEY
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Review> {
        val page = params.key ?: DEFAULT_REVIEWS_KEY

        if (movieId == null)
            return LoadResult.Invalid()

        val reviews = moviesRepository.getMovieReviews(movieId, page)
        return when (reviews) {
            is Resource.Success -> {
                if (reviews.data != null) {
                    val nextKey = if (reviews.data.isNullOrEmpty()) null else page + 1
                    LoadResult.Page(
                        reviews.data, prevKey = null, nextKey = nextKey
                    )
                }
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
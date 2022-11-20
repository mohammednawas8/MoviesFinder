package com.loc.moviesfinder.core_feature.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.loc.moviesfinder.core_feature.domain.util.Resource
import com.loc.moviesfinder.core_feature.domain.repository.MoviesRepository
import com.loc.moviesfinder.core_feature.domain.model.SearchedMovie
import java.lang.Exception

private const val DEFAULT_SEARCHED_KEY = 1

class SearchMoviesPagingSource(
    private val searchQuery: String,
    private val moviesRepository: MoviesRepository,
) : PagingSource<Int, SearchedMovie>() {


    override fun getRefreshKey(state: PagingState<Int, SearchedMovie>): Int {
        return DEFAULT_SEARCHED_KEY
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchedMovie> {
        val key = params.key ?: DEFAULT_SEARCHED_KEY
        return try {
            val result = moviesRepository.searchMovies(searchQuery, key)
            when (result) {
                is Resource.Success -> {
                    if (result.data != null) {
                        LoadResult.Page(result.data, key + 1, null)
                    } else {
                        LoadResult.Error(Throwable(""))
                    }
                }
                else -> LoadResult.Error(Throwable(""))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }
}
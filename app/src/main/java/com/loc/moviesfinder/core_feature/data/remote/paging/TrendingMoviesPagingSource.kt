package com.loc.moviesfinder.core_feature.data.remote.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.loc.moviesfinder.core_feature.data.mapper.toMovie
import com.loc.moviesfinder.core_feature.domain.model.Movie
import com.loc.moviesfinder.core_feature.domain.repository.MoviesRepository
import com.loc.moviesfinder.core_feature.presentation.image_viewer_screen.TAG
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException

const val DEFAULT_PAGE = 1

class TrendingMoviesPagingSource(
    private val moviesRepository: MoviesRepository,
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: DEFAULT_PAGE
        return try {
            val response = moviesRepository.getTrendingMovies(page)
            val responseBody = response.body()
            if (responseBody != null) {
                val nextKey = if (responseBody.total_pages == page) null else page + 1
                LoadResult.Page(
                    responseBody.results.map {
                        it.toMovie()
                    },
                    null,
                    nextKey
                )
            } else {
                throw Exception("Unknown error")
            }
        } catch (e: IOException) {
            e.printStackTrace()
            if (e is UnknownHostException) {
                LoadResult.Error(Exception("Check your internet connection"))
            } else
                LoadResult.Error(e)
        } catch (e: HttpException) {
            e.printStackTrace()
            LoadResult.Error(Throwable(getResponseError(e.code())))
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
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
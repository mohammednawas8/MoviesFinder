package com.loc.moviesfinder.core_feature.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.loc.moviesfinder.core_feature.data.remote.dao.Movie
import com.loc.moviesfinder.core_feature.data.util.MoviesGenre
import com.loc.moviesfinder.core_feature.domain.repository.MoviesRepository
import retrofit2.HttpException
import java.io.IOException

const val DEFAULT_PAGE = 1

class MoviesCollectionPagingSource(
    private val moviesGenre: MoviesGenre,
    private val moviesRepository: MoviesRepository,
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return DEFAULT_PAGE
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: DEFAULT_PAGE
        return try {
            val response = moviesRepository.getMoviesList(moviesGenre, page)
            val responseBody = response.body()
            if (responseBody != null) {
                val nextKey = if (responseBody.total_pages == page) null else page + 1
                LoadResult.Page(
                    responseBody.results,
                    null,
                    nextKey
                )
            } else {
                val code = response.code().toString().drop(0)
                if (code == "4") {
                    throw Exception("Please check your connection")
                } else if (code == "5") {
                    throw Exception("Server error")
                }
                throw Exception("Unknown error")
            }
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
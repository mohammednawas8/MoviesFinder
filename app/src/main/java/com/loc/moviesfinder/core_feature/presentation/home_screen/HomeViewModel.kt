package com.loc.moviesfinder.core_feature.presentation.home_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.loc.moviesfinder.core_feature.data.remote.paging.DefaultPaginator
import com.loc.moviesfinder.core_feature.data.remote.paging.TrendingMoviesPagingSource
import com.loc.moviesfinder.core_feature.domain.util.MoviesGenre
import com.loc.moviesfinder.core_feature.domain.repository.MoviesRepository
import com.loc.moviesfinder.core_feature.presentation.util.Constants.IMAGES_BASE_PATH
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

private val TAG = "HomeViewModel"

@HiltViewModel
class HomeViewModel @Inject constructor(
    moviesRepository: MoviesRepository,
) : ViewModel() {

    private var _tabLayoutMoviesState = MutableStateFlow(TabLayoutSectionState())
    val tabLayoutMoviesState: StateFlow<TabLayoutSectionState> = _tabLayoutMoviesState

    val trendingMovies = Pager(
        config = PagingConfig(10)
    ) {
        TrendingMoviesPagingSource(moviesRepository)
    }.flow.map { pagingData ->
        pagingData.map { movie ->
            movie.copy(coverPath = IMAGES_BASE_PATH + movie.coverPath)
        }
    }.cachedIn(viewModelScope)

    private val nowPlayingPaginator = DefaultPaginator(
        initialKey = 1,
        onLoadingUpdate = {
            _tabLayoutMoviesState.value = tabLayoutMoviesState.value.copy(isLoading = it)
        },
        onRequest = {
            moviesRepository.getMoviesList(MoviesGenre.NOW_PLAYING, it)
        },
        getNextKey = {
            it + 1
        },
        onSuccess = { key, items ->
            val newMovies = items.map { it.copy(coverPath = IMAGES_BASE_PATH + it.coverPath) }
            _tabLayoutMoviesState.value = tabLayoutMoviesState.value.copy(
                nowPlayingMovies = tabLayoutMoviesState.value.nowPlayingMovies + newMovies
            )
        },
        onError = {
            _tabLayoutMoviesState.value = tabLayoutMoviesState.value.copy(
                error = it?.message ?: ""
            )
            Log.e(TAG, "PlayingNowMovies ${it?.message}")
        }
    )

    init {
        loadNowPlayingMovies()
    }

    fun loadNowPlayingMovies() {
        viewModelScope.launch {
            nowPlayingPaginator.loadNextData()
        }
    }
}
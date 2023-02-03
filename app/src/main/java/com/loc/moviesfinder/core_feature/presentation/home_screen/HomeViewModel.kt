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
import kotlinx.coroutines.flow.collectLatest
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
            Log.d("test nowPlaying", items.toString())
            updateTabLayoutMovies()
        },
        onError = {
            _tabLayoutMoviesState.value = tabLayoutMoviesState.value.copy(
                error = it?.message ?: ""
            )
            Log.e(TAG, "PlayingNow Movies ${it?.message}")
        }
    )

    private val upcomingPaginator = DefaultPaginator(
        initialKey = 1,
        onLoadingUpdate = {
            _tabLayoutMoviesState.value = tabLayoutMoviesState.value.copy(isLoading = it)
        },
        onRequest = {
            moviesRepository.getMoviesList(MoviesGenre.UPCOMING, it)
        },
        getNextKey = {
            it + 1
        },
        onSuccess = { key, items ->
            val newMovies = items.map { it.copy(coverPath = IMAGES_BASE_PATH + it.coverPath) }
            _tabLayoutMoviesState.value = tabLayoutMoviesState.value.copy(
                upcomingMovies = tabLayoutMoviesState.value.upcomingMovies + newMovies
            )
            updateTabLayoutMovies()
        },
        onError = {
            _tabLayoutMoviesState.value = tabLayoutMoviesState.value.copy(
                error = it?.message ?: ""
            )
            Log.e(TAG, "Upcoming Movies ${it?.message}")
        }
    )

    private val topRatedPaginator = DefaultPaginator(
        initialKey = 1,
        onLoadingUpdate = {
            _tabLayoutMoviesState.value = tabLayoutMoviesState.value.copy(isLoading = it)
        },
        onRequest = {
            moviesRepository.getMoviesList(MoviesGenre.TOP_RATED, it)
        },
        getNextKey = {
            it + 1
        },
        onSuccess = { key, items ->
            val newMovies = items.map { it.copy(coverPath = IMAGES_BASE_PATH + it.coverPath) }
            _tabLayoutMoviesState.value = tabLayoutMoviesState.value.copy(
                topRatedMovies = tabLayoutMoviesState.value.topRatedMovies + newMovies
            )
            updateTabLayoutMovies()
        },
        onError = {
            _tabLayoutMoviesState.value = tabLayoutMoviesState.value.copy(
                error = it?.message ?: ""
            )
            Log.e(TAG, "Top Rated Movies ${it?.message}")
        }
    )

    private val popularPaginator = DefaultPaginator(
        initialKey = 1,
        onLoadingUpdate = {
            _tabLayoutMoviesState.value = tabLayoutMoviesState.value.copy(isLoading = it)
        },
        onRequest = {
            moviesRepository.getMoviesList(MoviesGenre.POPULAR, it)
        },
        getNextKey = {
            it + 1
        },
        onSuccess = { key, items ->
            val newMovies = items.map { it.copy(coverPath = IMAGES_BASE_PATH + it.coverPath) }
            _tabLayoutMoviesState.value = tabLayoutMoviesState.value.copy(
                popularMovies = tabLayoutMoviesState.value.popularMovies + newMovies
            )
            updateTabLayoutMovies()
        },
        onError = {
            _tabLayoutMoviesState.value = tabLayoutMoviesState.value.copy(
                error = it?.message ?: ""
            )
            Log.e(TAG, "Popular Movies ${it?.message}")
        }
    )

    private val latestPaginator = DefaultPaginator(
        initialKey = 1,
        onLoadingUpdate = {
            _tabLayoutMoviesState.value = tabLayoutMoviesState.value.copy(isLoading = it)
        },
        onRequest = {
            moviesRepository.getMoviesList(MoviesGenre.LATEST, it)
        },
        getNextKey = {
            it + 1
        },
        onSuccess = { key, items ->
            val newMovies = items.map { it.copy(coverPath = IMAGES_BASE_PATH + it.coverPath) }
            _tabLayoutMoviesState.value = tabLayoutMoviesState.value.copy(
                latestMovies = tabLayoutMoviesState.value.latestMovies + newMovies
            )
            updateTabLayoutMovies()
        },
        onError = {
            _tabLayoutMoviesState.value = tabLayoutMoviesState.value.copy(
                error = it?.message ?: ""
            )
            Log.e(TAG, "Latest Movies ${it?.message}")
        }
    )

    init {
        loadNowPlayingMovies()
    }

    private fun loadNowPlayingMovies() {
        viewModelScope.launch {
            nowPlayingPaginator.loadNextData()
        }
    }

    private fun loadUpcomingMovies() {
        viewModelScope.launch {
            upcomingPaginator.loadNextData()
        }
    }

    private fun loadTopRatedMovies() {
        viewModelScope.launch {
            topRatedPaginator.loadNextData()
        }
    }

    private fun loadPopularMovies() {
        viewModelScope.launch {
            popularPaginator.loadNextData()
        }
    }

    private fun loadLatestMovies() {
        viewModelScope.launch {
            latestPaginator.loadNextData()
        }
    }

    private fun updateTabLayoutMovies() {
        viewModelScope.launch {
            val genreTab = _tabLayoutMoviesState.value.selectedTab
            when (genreTab) {
                MoviesGenre.NOW_PLAYING -> _tabLayoutMoviesState.emit(tabLayoutMoviesState.value.copy(
                    tabLayoutMovies = tabLayoutMoviesState.value.nowPlayingMovies))

                MoviesGenre.POPULAR -> _tabLayoutMoviesState.emit(tabLayoutMoviesState.value.copy(
                    tabLayoutMovies = tabLayoutMoviesState.value.popularMovies))

                MoviesGenre.LATEST -> _tabLayoutMoviesState.emit(tabLayoutMoviesState.value.copy(
                    tabLayoutMovies = tabLayoutMoviesState.value.latestMovies))

                MoviesGenre.UPCOMING -> {
                    _tabLayoutMoviesState.emit(tabLayoutMoviesState.value.copy(
                        tabLayoutMovies = tabLayoutMoviesState.value.upcomingMovies))
                }

                MoviesGenre.TOP_RATED,
                -> _tabLayoutMoviesState.emit(tabLayoutMoviesState.value.copy(tabLayoutMovies = tabLayoutMoviesState.value.topRatedMovies))
                else -> _tabLayoutMoviesState.emit(tabLayoutMoviesState.value.copy(tabLayoutMovies = emptyList()))
            }
        }
    }

    private var isUpcomingCalled = false
    private var isTopRatedCalled = false
    private var isPopularCalled = false
    private var isLatestCalled = false

    fun changeMoviesTab(genreTab: MoviesGenre) {
        when (genreTab) {
            MoviesGenre.NOW_PLAYING ->
                _tabLayoutMoviesState.value =
                    tabLayoutMoviesState.value.copy(selectedTab = MoviesGenre.NOW_PLAYING,
                        selectedIndex = 0)

            MoviesGenre.POPULAR -> {
                _tabLayoutMoviesState.value =
                    tabLayoutMoviesState.value.copy(selectedTab = MoviesGenre.POPULAR,
                        selectedIndex = 3)
                if (!isPopularCalled){
                    loadPopularMovies()
                    isPopularCalled = true
                }
            }

            MoviesGenre.LATEST -> {
                _tabLayoutMoviesState.value =
                    tabLayoutMoviesState.value.copy(selectedTab = MoviesGenre.LATEST,
                        selectedIndex = 4)
                if (!isLatestCalled){
                    loadLatestMovies()
                    isLatestCalled = true
                }
            }
            MoviesGenre.UPCOMING -> {
                _tabLayoutMoviesState.value =
                    tabLayoutMoviesState.value.copy(selectedTab = MoviesGenre.UPCOMING,
                        selectedIndex = 1)
                if (!isUpcomingCalled){
                    loadUpcomingMovies()
                    isUpcomingCalled = true
                }
            }

            MoviesGenre.TOP_RATED -> {
                _tabLayoutMoviesState.value =
                    tabLayoutMoviesState.value.copy(selectedTab = MoviesGenre.TOP_RATED,
                        selectedIndex = 2)

                if (!isTopRatedCalled){
                    loadTopRatedMovies()
                    isTopRatedCalled = true
                }
            }

            else -> _tabLayoutMoviesState.value =
                tabLayoutMoviesState.value.copy(tabLayoutMovies = emptyList())
        }
        updateTabLayoutMovies()
    }


    fun loadNextPage() {
        viewModelScope.launch {
            val genreTab = _tabLayoutMoviesState.value.selectedTab
            when (genreTab) {
                MoviesGenre.NOW_PLAYING -> loadNowPlayingMovies()

                MoviesGenre.POPULAR -> loadPopularMovies()

                MoviesGenre.LATEST -> loadLatestMovies()

                MoviesGenre.UPCOMING -> loadUpcomingMovies()

                MoviesGenre.TOP_RATED -> loadTopRatedMovies()
                else -> _tabLayoutMoviesState.emit(tabLayoutMoviesState.value.copy(tabLayoutMovies = emptyList()))
            }
        }
    }
}
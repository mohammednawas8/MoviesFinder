package com.loc.moviesfinder.core_feature.presentation.search_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loc.moviesfinder.core_feature.data.remote.paging.DefaultPaginator
import com.loc.moviesfinder.core_feature.domain.model.SearchedMovie
import com.loc.moviesfinder.core_feature.domain.repository.MoviesRepository
import com.loc.moviesfinder.core_feature.presentation.util.Constants.IMAGES_BASE_PATH
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private val TAG = "SearchViewModel"

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
) : ViewModel() {

    private val _searchState = MutableStateFlow(SearchState())
    val searchState: StateFlow<SearchState> = _searchState

    private var shouldReset = false

    private val searchPager = DefaultPaginator(
        initialKey = 1,
        onLoadingUpdate = {
            if (shouldReset)
                _searchState.value = searchState.value.copy(newSearchLoading = it, pagingLoading = false)
            else
                _searchState.value = searchState.value.copy(newSearchLoading = false, pagingLoading = it)
        },
        onRequest = {
            moviesRepository.searchMovies(searchState.value.searchQuery, it)
        },
        getNextKey = {
            it + 1
        },
        onSuccess = { key, items ->
            val searchedMovies = items.map { it.copy(posterPath = IMAGES_BASE_PATH + it.posterPath) }
            if (shouldReset)
                _searchState.value =
                    searchState.value.copy(searchedMovies = searchedMovies)
            else
                _searchState.value =
                    searchState.value.copy(searchedMovies = searchState.value.searchedMovies + searchedMovies)
        },
        onError = {
            _searchState.value = searchState.value.copy(error = it?.message)
            Log.e(TAG, "PlayingNowMovies ${it?.message}")
        }
    )


    private var job: Job? = null
    fun searchMovies(searchQuery: String) {
        job?.cancel()
        job = viewModelScope.launch {
            delay(500)
            val oldSearchQuery = searchState.value.searchQuery
            shouldReset = oldSearchQuery.trim() != searchQuery.trim()
            _searchState.value = searchState.value.copy(searchQuery = searchQuery)
            if (shouldReset) {
                searchPager.reset()
                _searchState.value = searchState.value.copy(searchedMovies = emptyList())
            }
            val trimmedQuery = searchQuery.trim()
            if (trimmedQuery.isNotEmpty()) {
                searchPager.loadNextData()
            }
        }
    }

    fun loadNextMovies() {
        viewModelScope.launch {
            shouldReset = false
            searchPager.loadNextData()
        }
    }
}
package com.loc.moviesfinder.core_feature.presentation.watch_list_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loc.moviesfinder.core_feature.domain.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchListViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(WatchListState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            moviesRepository.getSavedMovies().collectLatest {
                _state.value = state.value.copy(moviesList = it, empty = it.isEmpty())
            }
        }
    }
}
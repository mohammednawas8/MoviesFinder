package com.loc.moviesfinder.core_feature.presentation.navigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NavigationViewModel : ViewModel() {
    private val _bottomNavigationState = mutableStateOf(BottomNavigationState())
    val bottomNavigationState: State<BottomNavigationState> = _bottomNavigationState

    private val _movieNavigation = MutableSharedFlow<Int?>()
    val movieNavigation = _movieNavigation.asSharedFlow()

    private val _bottomNavigation = MutableSharedFlow<String>()
    val bottomNavigation = _bottomNavigation.asSharedFlow()

    fun navigateToBottomNavigationScreen(selectedTab: Navigation) {
        _bottomNavigationState.value = BottomNavigationState(selectedTab = selectedTab)
        viewModelScope.launch {
            _bottomNavigation.emit(selectedTab.root)
        }
    }

    fun navigateToMovie(id: Int) {
        viewModelScope.launch {
            _movieNavigation.emit(id)
        }
        _bottomNavigationState.value = bottomNavigationState.value.copy(showBottomNavigation = false)
    }

    fun changeSelectedItem(selectedTab: Navigation) {
        _bottomNavigationState.value = BottomNavigationState(selectedTab = selectedTab)
    }
}
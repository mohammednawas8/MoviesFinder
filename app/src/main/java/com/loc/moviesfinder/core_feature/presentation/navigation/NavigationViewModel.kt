package com.loc.moviesfinder.core_feature.presentation.navigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class NavigationViewModel : ViewModel() {
    private val _bottomNavigationState = mutableStateOf(BottomNavigationState())
    val bottomNavigationState: MutableState<BottomNavigationState> = _bottomNavigationState

    private val _navigation = MutableSharedFlow<String>()
    val navigation = _navigation.asSharedFlow()

    fun navigate(selectedTab: Navigation) {
        _bottomNavigationState.value = BottomNavigationState(selectedTab = selectedTab)
        viewModelScope.launch {
            _navigation.emit(selectedTab.root)
        }
    }

    fun changeSelectedItem(selectedTab: Navigation){
        _bottomNavigationState.value = BottomNavigationState(selectedTab = selectedTab)
    }
}
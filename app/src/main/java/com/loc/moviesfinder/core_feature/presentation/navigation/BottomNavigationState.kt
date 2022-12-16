package com.loc.moviesfinder.core_feature.presentation.navigation

data class BottomNavigationState(
    val selectedTab: Navigation = Navigation.HomeScreen,
    val showBottomNavigation: Boolean = true
) {
}
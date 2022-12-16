package com.loc.moviesfinder.core_feature.presentation.navigation

import android.util.Log
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.loc.moviesfinder.core_feature.presentation.home_screen.HomeScreen
import com.loc.moviesfinder.core_feature.presentation.home_screen.HomeViewModel
import com.loc.moviesfinder.core_feature.presentation.search_screen.SearchScreen
import com.loc.moviesfinder.core_feature.presentation.search_screen.SearchViewModel
import com.loc.moviesfinder.core_feature.presentation.watch_list_screen.WatchListScreen
import com.loc.moviesfinder.core_feature.presentation.watch_list_screen.WatchListViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun NavigationScreen(
    homeViewModel: HomeViewModel,
    searchViewModel: SearchViewModel,
    watchListViewModel: WatchListViewModel,
) {
    val navController = rememberNavController()

    val navigationViewModel: NavigationViewModel = hiltViewModel()

    val state = navigationViewModel.bottomNavigationState.value

    LaunchedEffect(key1 = true) {
        navigationViewModel.navigation.collect {
            navController.navigate(it) {
                popUpTo(navController.graph.startDestinationId) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    }

    val stackState = navController.currentBackStackEntryAsState().value
    if (stackState != null) {
        when (stackState.destination.route) {
            Navigation.HomeScreen.root -> navigationViewModel.changeSelectedItem(Navigation.HomeScreen)
            Navigation.SearchScreen.root -> navigationViewModel.changeSelectedItem(Navigation.SearchScreen)
            Navigation.WatchListScreen.root -> navigationViewModel.changeSelectedItem(Navigation.WatchListScreen)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            MoviesBottomNavigation(onHomeClick = {
                navigationViewModel.navigate(Navigation.HomeScreen)
            },
                onSearchClick = {
                    navigationViewModel.navigate(Navigation.SearchScreen)
                },
                onWatchListClick = {
                    navigationViewModel.navigate(Navigation.WatchListScreen)

                }, selectedItem = state.selectedTab
            )
        }, content = { paddingValues ->
            val bottomPadding = paddingValues.calculateBottomPadding()
            NavHost(navController = navController,
                startDestination = Navigation.HomeScreen.root,
                modifier = Modifier.padding(bottom = bottomPadding)) {

                composable(Navigation.HomeScreen.root) {
                    HomeScreen(navController = navController,
                        homeViewModel,
                        navigateToSearch = { navigationViewModel.navigate(Navigation.SearchScreen) })
                }

                composable(Navigation.SearchScreen.root) {
                    SearchScreen(navController = navController, searchViewModel)
                }

                composable(Navigation.WatchListScreen.root) {
                    WatchListScreen(navController = navController, watchListViewModel)
                }
            }
        }
    )
}
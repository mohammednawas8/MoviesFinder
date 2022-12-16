package com.loc.moviesfinder.core_feature.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.loc.moviesfinder.core_feature.presentation.details_screen.DetailsScreen
import com.loc.moviesfinder.core_feature.presentation.home_screen.HomeScreen
import com.loc.moviesfinder.core_feature.presentation.home_screen.HomeViewModel
import com.loc.moviesfinder.core_feature.presentation.search_screen.SearchScreen
import com.loc.moviesfinder.core_feature.presentation.search_screen.SearchViewModel
import com.loc.moviesfinder.core_feature.presentation.watch_list_screen.WatchListScreen
import com.loc.moviesfinder.core_feature.presentation.watch_list_screen.WatchListViewModel

@Composable
fun NavigationScreen(
    homeViewModel: HomeViewModel,
    searchViewModel: SearchViewModel,
    watchListViewModel: WatchListViewModel,
) {
    val navController = rememberNavController()

    val navigationViewModel: NavigationViewModel = hiltViewModel()

    val state = navigationViewModel.bottomNavigationState.value

    //navigate to BottomNavigation screens
    LaunchedEffect(key1 = true) {
        navigationViewModel.bottomNavigation.collect {
            navController.navigate(it) {
                popUpTo(navController.graph.startDestinationId) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    }

    //navigate to MovieDetails screen
    LaunchedEffect(key1 = true) {
        navigationViewModel.movieNavigation.collect {
            it?.let { movieId ->
                navController.navigate(Navigation.MovieDetailsScreen.root + "/$movieId"){
                    restoreState = true
                }
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
            if (state.showBottomNavigation)
                MoviesBottomNavigation(onHomeClick = {
                    navigationViewModel.navigateToBottomNavigationScreen(Navigation.HomeScreen)
                },
                    onSearchClick = {
                        navigationViewModel.navigateToBottomNavigationScreen(Navigation.SearchScreen)
                    },
                    onWatchListClick = {
                        navigationViewModel.navigateToBottomNavigationScreen(Navigation.WatchListScreen)

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
                        navigateToSearch = {
                            navigationViewModel.navigateToBottomNavigationScreen(Navigation.SearchScreen)
                        },
                        navigateToMovie = { navigationViewModel.navigateToMovie(it.id) }
                    )
                }

                composable(Navigation.SearchScreen.root) {
                    SearchScreen(navController = navController, searchViewModel)
                }

                composable(Navigation.WatchListScreen.root) {
                    WatchListScreen(navController = navController, watchListViewModel)
                }

                composable(Navigation.MovieDetailsScreen.root + "/{movieId}",
                    arguments = listOf(
                        navArgument(name = "movieId") {
                            type = NavType.StringType
                            nullable = false
                        })
                ) {
                    val movieId = it.arguments?.getString("movieId")?.toInt()
                    movieId?.let {
                        DetailsScreen(movieId = it, navController = navController)
                    }
                }
            }
        }
    )
}
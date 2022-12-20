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
import com.loc.moviesfinder.core_feature.presentation.image_viewer_screen.ImageType
import com.loc.moviesfinder.core_feature.presentation.image_viewer_screen.ImageViewerScreen
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
                navController.navigate(Navigation.MovieDetailsScreen.root + "/$movieId") {
                    restoreState = true
                }
            }
        }
    }

    //Navigate to ImageViewer screen
    LaunchedEffect(key1 = true) {
        navigationViewModel.imageViewerNavigation.collect {
            navController.navigate(Navigation.ImageViewerScreen.root + "/${it.path}/${it.type}")
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
                    HomeScreen(
                        homeViewModel,
                        navigateToSearch = {
                            navigationViewModel.navigateToBottomNavigationScreen(Navigation.SearchScreen)
                        },
                        navigateToMovie = { navigationViewModel.navigateToMovie(it.id) }
                    )
                }

                composable(Navigation.SearchScreen.root) {
                    SearchScreen(searchViewModel,
                        navigateToHome = {navigationViewModel.navigateToBottomNavigationScreen(Navigation.HomeScreen)},
                        onClick = { navigationViewModel.navigateToMovie(it.id) })
                }

                composable(Navigation.WatchListScreen.root) {
                    WatchListScreen(navController = navController,
                        watchListViewModel,
                        onClick = { navigationViewModel.navigateToMovie(it.id) })
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
                        DetailsScreen(movieId = it,
                            navController = navController,
                            onImageClick = { image, type ->
                                navigationViewModel.navigateToImageViewer(image, type)
                            })
                    }
                }

                composable(Navigation.ImageViewerScreen.root + "/{imagePath}/{type}") {
                    val imagePath = it.arguments?.getString("imagePath") ?: ""
                    val type = it.arguments?.getString("type").let {
                        if (it == "Poster")
                            ImageType.POSTER
                        else
                            ImageType.BACKDROP
                    }
                    ImageViewerScreen(imagePath = imagePath, type = type)
                }
            }
        }
    )
}
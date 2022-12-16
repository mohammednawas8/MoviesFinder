package com.loc.moviesfinder.core_feature.presentation.navigation

sealed class Navigation(val root: String) {
    object SplashScreen : Navigation("splash_screen")
    object HomeScreen : Navigation("home_screen")
    object SearchScreen : Navigation("search_screen")
    object MovieDetailsScreen : Navigation("movie_details_screen")
    object ImageViewerScreen : Navigation("image_view_screen")
    object WatchListScreen : Navigation("watch_list_screen")
}
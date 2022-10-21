package com.loc.moviesfinder.core_feature.presentation.home_screen

import com.loc.moviesfinder.core_feature.domain.model.Movie

data class TabLayoutSectionState(
    val isLoading: Boolean = false,
    val error: String = "",
    val nowPlayingMovies: List<Movie> = emptyList(),
    val upcomingMovies: List<Movie> = emptyList(),
    val topRatedMovies: List<Movie> = emptyList(),
    val popularMovies: List<Movie> = emptyList(),
    val latestMovies: List<Movie> = emptyList(),
)

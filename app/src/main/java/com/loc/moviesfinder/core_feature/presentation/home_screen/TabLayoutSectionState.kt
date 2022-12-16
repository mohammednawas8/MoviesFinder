package com.loc.moviesfinder.core_feature.presentation.home_screen

import com.loc.moviesfinder.core_feature.domain.model.Movie
import com.loc.moviesfinder.core_feature.domain.util.MoviesGenre

data class TabLayoutSectionState(
    val isLoading: Boolean = false,
    val error: String = "",
    val selectedTab: MoviesGenre = MoviesGenre.NOW_PLAYING,
    val selectedIndex: Int = 0,
    val tabLayoutMovies: List<Movie> = emptyList(),
    val nowPlayingMovies: List<Movie> = emptyList(),
    val upcomingMovies: List<Movie> = emptyList(),
    val topRatedMovies: List<Movie> = emptyList(),
    val popularMovies: List<Movie> = emptyList(),
    val latestMovies: List<Movie> = emptyList(),
)

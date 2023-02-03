package com.loc.moviesfinder.core_feature.presentation.watch_list_screen

import com.loc.moviesfinder.core_feature.domain.model.MovieDetails

data class WatchListState(
    val moviesList: List<MovieDetails> = emptyList(),
    val empty: Boolean = false
) {
}
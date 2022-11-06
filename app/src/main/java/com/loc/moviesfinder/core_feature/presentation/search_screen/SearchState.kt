package com.loc.moviesfinder.core_feature.presentation.search_screen

import com.loc.moviesfinder.core_feature.domain.model.SearchedMovie

data class SearchState(
    val searchedMovies: List<SearchedMovie> = emptyList(),
    val newSearchLoading: Boolean = false,
    val pagingLoading: Boolean = false,
    val searchQuery: String = "",
    val error: String? = null,
)

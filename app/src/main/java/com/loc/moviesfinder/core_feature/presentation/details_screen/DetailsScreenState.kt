package com.loc.moviesfinder.core_feature.presentation.details_screen

import com.loc.moviesfinder.core_feature.domain.model.MovieDetails

data class DetailsScreenState(
    val movieDetails: MovieDetails? = null,
    val aboutMovie: String = "",
    val isSaved: Boolean = false,
    val error: String = "",
    val isLoading: Boolean = false

)

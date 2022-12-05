package com.loc.moviesfinder.core_feature.presentation.details_screen

import com.loc.moviesfinder.core_feature.domain.model.Cast
import com.loc.moviesfinder.core_feature.domain.model.MovieDetails

data class DetailsScreenState(
    val movieDetails: MovieDetails? = null,
    val castList: List<Cast> = emptyList(),
    val aboutMovie: String = "",
    val isSaved: Boolean = false,
    val error: String? = null,
    val detailsLoading: Boolean = false,
    val castLoading: Boolean = false,
)

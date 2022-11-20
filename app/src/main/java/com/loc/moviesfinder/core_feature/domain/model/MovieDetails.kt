package com.loc.moviesfinder.core_feature.domain.model

data class MovieDetails(
    val id: Int,
    val title: String,
    val averageVoting: Float,
    val backdropPath: String?,
    val posterPath: String,
    val releaseYear: Int,
    val durationInMinutes: Int?,
    val genre: List<String>,
    val aboutMovie: String
)
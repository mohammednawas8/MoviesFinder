package com.loc.moviesfinder.core_feature.presentation.search_screen

data class SearchedMovie(
    val id: Int,
    val coverPath: String,
    val title: String,
    val voteAverage: Double,
    val category: String,
    val year: Int,
    val durationInMinutes: Int
)

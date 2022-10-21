package com.loc.moviesfinder.core_feature.data.remote.dao

data class MoviesCollectionResponse(
    val dates: Dates,
    val page: Int,
    val results: List<MovieResult>,
    val total_pages: Int,
    val total_results: Int
)
package com.loc.moviesfinder.core_feature.data.remote.dao

data class ReviewsResponse(
    val id: Int,
    val page: Int,
    val results: List<Reviewer>,
    val total_pages: Int,
    val total_results: Int
)
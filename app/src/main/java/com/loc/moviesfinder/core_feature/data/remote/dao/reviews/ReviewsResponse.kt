package com.loc.moviesfinder.core_feature.data.remote.dao.reviews

data class ReviewsResponse(
    val id: Int,
    val page: Int,
    val results: List<ReviewResult>,
    val total_pages: Int,
    val total_results: Int
)
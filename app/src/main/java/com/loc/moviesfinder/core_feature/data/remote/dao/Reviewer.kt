package com.loc.moviesfinder.core_feature.data.remote.dao

data class Reviewer(
    val author: String,
    val reviewer_details: ReviewerDetails,
    val content: String,
    val created_at: String,
    val id: String,
    val updated_at: String,
    val url: String,
)
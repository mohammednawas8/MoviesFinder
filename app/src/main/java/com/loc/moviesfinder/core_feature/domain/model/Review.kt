package com.loc.moviesfinder.core_feature.domain.model

data class Review(
    val author: String,
    val rating: Double?,
    val avatarPath: String?,
    val content: String,
)
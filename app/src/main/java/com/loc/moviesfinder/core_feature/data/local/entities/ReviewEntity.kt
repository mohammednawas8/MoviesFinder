package com.loc.moviesfinder.core_feature.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ReviewEntity(
    @PrimaryKey val reviewId: String,
    val name: String,
    val content: String,
    val avatarPath: String? = null,
    val rate: Int? = null,
    val movieId: Int,
)

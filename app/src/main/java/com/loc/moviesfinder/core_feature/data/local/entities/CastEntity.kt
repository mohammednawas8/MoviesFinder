package com.loc.moviesfinder.core_feature.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CastEntity(
    @PrimaryKey val castId: Int,
    val name: String,
    val profilePath: String? = null,
    val movieId: Int,
)

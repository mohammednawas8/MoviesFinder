package com.loc.moviesfinder.core_feature.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieEntity(
    @PrimaryKey val movieId: Int,
    val releaseDate: String,
    val aboutMovie: String,
    val rate: Double,
    val durationInMinutes: Int? = null,
)

package com.loc.moviesfinder.core_feature.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieEntity(
    @PrimaryKey val movieId: Int,
    val genre: String,
    val title: String,
    val releaseYear: Int,
    val aboutMovie: String,
    val rate: Double,
    val cover: String,
    val banner: String,
    val durationInMinutes: Int? = null,
)

package com.loc.moviesfinder.core_feature.data.local.entities

import androidx.room.Entity

@Entity(primaryKeys = ["movieId","genre"])
data class GenreEntity(
    val movieId: Int,
    val genre: String
)

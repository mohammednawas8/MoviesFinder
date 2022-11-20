package com.loc.moviesfinder.core_feature.data.local.entities

import androidx.room.Embedded
import androidx.room.Relation

data class MovieWithGenres(
    @Embedded val movieEntity: MovieEntity,
    @Relation(
        parentColumn = "movieId",
        entityColumn = "movieId"
    )
    val genres: List<GenreEntity>
)

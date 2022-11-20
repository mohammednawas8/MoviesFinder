package com.loc.moviesfinder.core_feature.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.loc.moviesfinder.core_feature.data.local.entities.CastEntity
import com.loc.moviesfinder.core_feature.data.local.entities.GenreEntity
import com.loc.moviesfinder.core_feature.data.local.entities.MovieEntity
import com.loc.moviesfinder.core_feature.data.local.entities.ReviewEntity

@Database(
    entities = [
        MovieEntity::class, GenreEntity::class,
        CastEntity::class, ReviewEntity::class
    ],
    version = 1
)
abstract class MoviesDatabase : RoomDatabase() {
    abstract val dao: MoviesDao
}
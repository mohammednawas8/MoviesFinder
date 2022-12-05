package com.loc.moviesfinder.core_feature.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.loc.moviesfinder.core_feature.data.local.entities.MovieEntity

@Database(
    entities = [
        MovieEntity::class
    ],
    version = 2
)
abstract class MoviesDatabase : RoomDatabase() {
    abstract val dao: MoviesDao
}
package com.loc.moviesfinder.core_feature.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.loc.moviesfinder.core_feature.data.local.entities.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {

    @Insert
    suspend fun insertMovie(movieEntity: MovieEntity)

    @Query("DELETE FROM MovieEntity WHERE movieId=:movieId")
    suspend fun deleteMovieById(movieId: Int)

    @Query("SELECT * FROM MovieEntity WHERE movieId=:movieId")
    suspend fun getMovieById(movieId: Int): MovieEntity?

}
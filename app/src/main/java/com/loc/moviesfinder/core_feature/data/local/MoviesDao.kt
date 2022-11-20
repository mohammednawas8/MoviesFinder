package com.loc.moviesfinder.core_feature.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.loc.moviesfinder.core_feature.data.local.entities.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {

    @Insert
    fun insertMovie(movieEntity: MovieEntity)

    @Insert
    fun insertGenre(genresEntity: List<GenreEntity>)

    @Insert
    fun insertReviews(reviewsEntity: List<ReviewEntity>)

    @Insert
    fun insertCasts(castsEntity: List<CastEntity>)

    @Query("SELECT * FROM MovieEntity")
    fun getMovies(): Flow<List<MovieWithGenres>>

    @Query("SELECT * FROM MovieEntity WHERE movieId=:id")
    suspend fun getMovieById(id: Int): MovieWithGenres?
}
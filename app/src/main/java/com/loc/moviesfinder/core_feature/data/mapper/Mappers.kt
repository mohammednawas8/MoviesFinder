package com.loc.moviesfinder.core_feature.data.mapper

import android.util.Log
import com.loc.moviesfinder.core_feature.data.local.entities.MovieEntity
import com.loc.moviesfinder.core_feature.data.local.entities.MovieWithGenres
import com.loc.moviesfinder.core_feature.data.remote.dao.MovieDetailsResponse
import com.loc.moviesfinder.core_feature.data.remote.dao.MovieResult
import com.loc.moviesfinder.core_feature.domain.model.Movie
import com.loc.moviesfinder.core_feature.domain.model.MovieDetails

fun MovieResult.toMovie(): Movie {
    return Movie(
        id, poster_path
    )
}


fun MovieDetailsResponse.toMovieDetails(): MovieDetails {
    val year = if (release_date.isBlank()) 1234
    else
        release_date.split("-")[0].toInt()

    val genres = genreResults?.map { it.name } ?: emptyList()
    return MovieDetails(id,
        title,
        vote_average.toFloat(),
        backdrop_path,
        poster_path ?: "",
        year,
        runtime,
        genres,
        overview ?: "")
}

fun MovieWithGenres.toMoviesDetails(): MovieDetails {
    val movie = movieEntity
    val genres = genres.map { it.genre }
    return MovieDetails(movie.movieId,
        movie.title,
        movie.rate.toFloat(),
        movie.banner,
        movie.cover,
        movie.releaseYear,
        movie.durationInMinutes,
        genres,
        movie.aboutMovie)
}


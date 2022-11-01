package com.loc.moviesfinder.core_feature.data.mapper

import com.loc.moviesfinder.core_feature.data.local.entities.CastEntity
import com.loc.moviesfinder.core_feature.data.local.entities.ReviewEntity
import com.loc.moviesfinder.core_feature.data.remote.dao.Cast
import com.loc.moviesfinder.core_feature.data.remote.dao.MovieDetailsResponse
import com.loc.moviesfinder.core_feature.data.remote.dao.MovieResult
import com.loc.moviesfinder.core_feature.data.remote.dao.Reviewer
import com.loc.moviesfinder.core_feature.domain.model.Movie
import com.loc.moviesfinder.core_feature.domain.model.MovieDetails
import com.loc.moviesfinder.core_feature.domain.model.SearchedMovie
import java.time.Duration

fun MovieResult.toMovie(): Movie {
    return Movie(
        id, poster_path
    )
}


fun MovieDetailsResponse.toMovieDetails(): MovieDetails {
    val year = if (release_date.isBlank()) 1234
    else
        release_date.split("-")[0].toInt()
    val genres = genres.map { it.name }
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


package com.loc.moviesfinder.core_feature.data.mapper

import android.util.Log
import com.loc.moviesfinder.core_feature.data.local.entities.MovieWithGenres
import com.loc.moviesfinder.core_feature.data.remote.dao.MovieDetailsResponse
import com.loc.moviesfinder.core_feature.data.remote.dao.MovieResult
import com.loc.moviesfinder.core_feature.data.remote.dao.reviews.ReviewResult
import com.loc.moviesfinder.core_feature.data.remote.dao.reviews.ReviewsResponse
import com.loc.moviesfinder.core_feature.domain.model.Movie
import com.loc.moviesfinder.core_feature.domain.model.MovieDetails
import com.loc.moviesfinder.core_feature.domain.model.Review
import com.loc.moviesfinder.core_feature.presentation.util.Constants
import java.text.DecimalFormat

fun MovieResult.toMovie(): Movie {
    return Movie(
        id, poster_path
    )
}


fun MovieDetailsResponse.toMovieDetails(): MovieDetails {
    val year = if (release_date.isBlank()) 1234
    else
        release_date.split("-")[0].toInt()

    val genres = genres?.map { it.name } ?: emptyList()
    return MovieDetails(id,
        title,
        averageVotingConvertor(vote_average),
        backdrop_path,
        poster_path ?: "",
        year,
        runtime,
        genres,
        overview ?: ""
    )
}

fun MovieWithGenres.toMoviesDetails(): MovieDetails {
    val movie = movieEntity
    val genres = genres.map { it.genre }
    return MovieDetails(movie.movieId,
        movie.title,
        movie.rate,
        movie.banner,
        movie.cover,
        movie.releaseYear,
        movie.durationInMinutes,
        genres,
        movie.aboutMovie)
}

fun MovieDetails?.correctImagePath(): MovieDetails? {
    return this?.copy(posterPath = Constants.IMAGES_BASE_PATH + this.posterPath,
        backdropPath = Constants.IMAGES_BASE_PATH + this.backdropPath)
}

fun ReviewResult.toReview(): Review {
    return Review(
        author,
        author_details.rating,
        author_details.avatar_path,
        content
    )
}


fun averageVotingConvertor(averageRating: Double): Double {
    val df = DecimalFormat("#.#")
    return df.format(averageRating).toDouble()
}

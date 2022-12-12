package com.loc.moviesfinder.core_feature.data.mapper

import com.loc.moviesfinder.core_feature.data.local.entities.MovieEntity
import com.loc.moviesfinder.core_feature.data.remote.dao.MovieDetailsResponse
import com.loc.moviesfinder.core_feature.data.remote.dao.MovieResult
import com.loc.moviesfinder.core_feature.data.remote.dao.cast.CastResult
import com.loc.moviesfinder.core_feature.data.remote.dao.reviews.ReviewResult
import com.loc.moviesfinder.core_feature.domain.model.Cast
import com.loc.moviesfinder.core_feature.domain.model.Movie
import com.loc.moviesfinder.core_feature.domain.model.MovieDetails
import com.loc.moviesfinder.core_feature.domain.model.Review
import com.loc.moviesfinder.core_feature.presentation.util.Constants.IMAGES_BASE_PATH
import com.loc.moviesfinder.core_feature.presentation.util.toSingleLine
import com.loc.moviesfinder.core_feature.presentation.util.toStringListOfGenres
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

fun MovieDetails.toMovieEntity(): MovieEntity {
    return MovieEntity(id,
        title,
        genre.toSingleLine(),
        releaseYear,
        aboutMovie,
        averageVoting,
        posterPath,
        backdropPath ?: "",
        durationInMinutes)
}


fun MovieEntity.toMovieDetails(): MovieDetails {
    return MovieDetails(movieId,
        title,
        rate,
        banner,
        cover,
        releaseYear,
        durationInMinutes,
        genre.toStringListOfGenres(),
        aboutMovie)
}

fun MovieDetails?.correctImagePath(): MovieDetails? {
    return this?.copy(posterPath = IMAGES_BASE_PATH + this.posterPath,
        backdropPath = IMAGES_BASE_PATH + this.backdropPath)
}

fun String?.correctImagePath(): String {
    return IMAGES_BASE_PATH + this
}

fun ReviewResult.toReview(): Review {
    return Review(
        author,
        author_details.rating,
        author_details.avatar_path.correctImagePath(),
        content
    )
}


fun averageVotingConvertor(averageRating: Double): Double {
    val df = DecimalFormat("#.#")
    return df.format(averageRating).toDouble()
}

fun CastResult.toCast(): Cast {
    return Cast(profile_path.correctImagePath(), name)
}

package com.loc.moviesfinder.core_feature.data.mapper

import com.loc.moviesfinder.core_feature.data.local.entities.CastEntity
import com.loc.moviesfinder.core_feature.data.local.entities.MovieEntity
import com.loc.moviesfinder.core_feature.data.local.entities.ReviewEntity
import com.loc.moviesfinder.core_feature.data.remote.dao.Cast
import com.loc.moviesfinder.core_feature.data.remote.dao.Movie
import com.loc.moviesfinder.core_feature.data.remote.dao.Reviewer

fun Movie.toMovieEntity(movieDuration: Int?): MovieEntity {
    return MovieEntity(
        id, release_date, overview, vote_average, movieDuration
    )
}

fun Reviewer.toReviewEntity(movieId: Int): ReviewEntity {
    return ReviewEntity(
        id,
        reviewer_details.name,
        content,
        reviewer_details.avatar_path,
        reviewer_details.rating,
        movieId
    )
}

fun Cast.toCastEntity(movieId: Int): CastEntity {
    return CastEntity(cast_id, name, profile_path, movieId)
}


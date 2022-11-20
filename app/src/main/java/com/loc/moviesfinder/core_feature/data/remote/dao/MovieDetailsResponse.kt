package com.loc.moviesfinder.core_feature.data.remote.dao

data class MovieDetailsResponse(
    val adult: Boolean,
    val backdrop_path: String,
    val genreResults: List<Genre>?,
    val id: Int,
    val imdb_id: String?,
    val original_language: String,
    val original_title: String,
    val overview: String?,
    val popularity: Double,
    val poster_path: String?,
    val release_date: String,
    val revenue: Int,
    val runtime: Int,
    val spoken_languages: List<SpokenLanguage>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int,

    )
package com.loc.moviesfinder.core_feature.data.remote.dao

data class CreditsResponse(
    val cast: List<Cast>,
    val crew: List<Crew>,
    val id: Int
)
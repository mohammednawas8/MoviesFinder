package com.loc.moviesfinder.core_feature.data.remote.dao.cast

data class CastResponse(
    val cast: List<CastResult>,
    val id: Int
)
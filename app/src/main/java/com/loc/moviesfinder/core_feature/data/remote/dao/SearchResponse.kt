package com.loc.moviesfinder.core_feature.data.remote.dao

data class SearchResponse(
    val results: List<SearchResult>
)

data class SearchResult(
    val id: Int,
)

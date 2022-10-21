package com.loc.moviesfinder.core_feature.domain.paging

interface Paginator<Key, Item> {

    suspend fun loadNextData()

    fun reset()
}
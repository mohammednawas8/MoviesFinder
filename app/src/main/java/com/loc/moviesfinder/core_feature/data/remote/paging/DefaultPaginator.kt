package com.loc.moviesfinder.core_feature.data.remote.paging

import android.util.Log
import com.loc.moviesfinder.core_feature.domain.util.Resource
import com.loc.moviesfinder.core_feature.domain.paging.Paginator

class DefaultPaginator<Key, Item>(
    val initialKey: Key,
    private inline val onLoadingUpdate: (Boolean) -> Unit,
    private inline val onRequest: suspend (key: Key) -> Resource<List<Item>>,
    private inline val getNextKey: (key: Key) -> Key,
    private inline val onSuccess: suspend (key: Key, items: List<Item>) -> Unit,
    private inline val onError: suspend (Throwable?) -> Unit,
) : Paginator<Key, Item> {

    private var currentKey = initialKey
    private var isMakingRequest = false
    private var endPaging = false

    override suspend fun loadNextData() {
        if (isMakingRequest || endPaging) {
            onLoadingUpdate(false)
            return
        }

        isMakingRequest = true
        onLoadingUpdate(true)
        val result = onRequest(currentKey)
        //End paging condition
        if (result.data == null || result.data.size < 20) { // In each request we get 20 items, if the size is less than 20 this means that the next page will be empty
            endPaging = true
        }
        isMakingRequest = false
        if (result is Resource.Error) {
            onError(result.exception)
            onLoadingUpdate(false)
            return
        }
        val items = result.data
        currentKey = getNextKey(currentKey)
        items?.let { onSuccess(currentKey, it) }
        onLoadingUpdate(false)
    }

    override fun reset() {
        currentKey = initialKey
        endPaging = false
        isMakingRequest = false
    }
}
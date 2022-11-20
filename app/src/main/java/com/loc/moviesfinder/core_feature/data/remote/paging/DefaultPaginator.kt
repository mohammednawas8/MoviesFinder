package com.loc.moviesfinder.core_feature.data.remote.paging

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
            return
        }
        isMakingRequest = true
        onLoadingUpdate(true)
        val result = onRequest(currentKey)
        //End paging condition
        if (result.data == null) {
            endPaging = true
            return
        }
        isMakingRequest = false
        if (result is Resource.Error) {
            onError(result.exception)
            onLoadingUpdate(false)
            return
        }
        val items = result.data
        currentKey = getNextKey(currentKey)
        onSuccess(currentKey, items)
        onLoadingUpdate(false)
    }

    override fun reset() {
        currentKey = initialKey
        isMakingRequest = false
    }
}
package com.loc.moviesfinder.core_feature.presentation.util.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun ShimmerImageCard(
    modifier: Modifier = Modifier,
    path: String,
    contentDescription: String? = null,
) {

    val imageRequest = ImageRequest.Builder(LocalContext.current).data(path)
        .crossfade(true).build()

    var loading by remember {
        mutableStateOf(false)
    }

    Box(modifier = modifier) {
        AsyncImage(model = imageRequest,
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            onLoading = {
                loading = true
            },
            onSuccess = {
                loading = false
            },
            onError = {
                loading = false
            }
        )

        if (loading){
            ShimmerCard(modifier = Modifier.fillMaxSize())
        }
    }
}
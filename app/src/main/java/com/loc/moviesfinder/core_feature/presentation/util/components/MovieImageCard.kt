package com.loc.moviesfinder.core_feature.presentation.util.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.loc.moviesfinder.core_feature.presentation.util.ImageLoadingState


@Composable
fun MovieImageCard(
    modifier: Modifier = Modifier,
    imageUrl: String,
    shape: Shape
) {
    var isImageLoadingState by remember {
        mutableStateOf(ImageLoadingState.LOADING)
    }
    val context = LocalContext.current
    val imageRequest = ImageRequest
        .Builder(context)
        .data(imageUrl)
        .crossfade(200)
        .build()

    Card(modifier = modifier,
        shape = shape,
        elevation = 0.dp) {
        AsyncImage(
            model = imageRequest,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            onLoading = {
                isImageLoadingState = ImageLoadingState.LOADING
            },
            onSuccess = {
                isImageLoadingState = ImageLoadingState.SUCCESS
            },
            onError = {
                isImageLoadingState = ImageLoadingState.ERROR
            }
        )

        if (isImageLoadingState == ImageLoadingState.LOADING)
            ShimmerCard(2000f, modifier = Modifier.fillMaxSize())
        else if (isImageLoadingState == ImageLoadingState.ERROR)
            Spacer(Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.surface))

    }
}

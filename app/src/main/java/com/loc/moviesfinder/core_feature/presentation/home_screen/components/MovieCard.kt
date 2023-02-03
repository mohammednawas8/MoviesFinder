package com.loc.moviesfinder.core_feature.presentation.home_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.loc.moviesfinder.core_feature.domain.model.Movie
import com.loc.moviesfinder.core_feature.presentation.util.Constants.IMAGES_BASE_PATH
import com.loc.moviesfinder.core_feature.presentation.util.ImageLoadingState
import com.loc.moviesfinder.core_feature.presentation.util.components.ShimmerCard
import com.loc.moviesfinder.ui.theme.MoviesFinderTheme

@Composable
fun MovieCard(
    modifier: Modifier = Modifier,
    movieItem: Movie,
    onClick: (Movie) -> Unit,
) {
    var isImageLoadingState by remember {
        mutableStateOf(ImageLoadingState.LOADING)
    }
    val context = LocalContext.current
    val imageRequest = ImageRequest
        .Builder(context)
        .data(movieItem.coverPath)
        .crossfade(true)
        .build()

    Card(modifier = modifier.clickable { onClick(movieItem) },
        shape = MaterialTheme.shapes.large,
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
            ShimmerCard(2000f,modifier = Modifier.fillMaxSize())

        else if (isImageLoadingState == ImageLoadingState.ERROR)
            Spacer(Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.surface))

    }
}

@Preview
@Composable
fun MovieCardPreview() {
    MoviesFinderTheme {
        MovieCard(
            modifier = Modifier
                .width(139.dp)
                .height(210.dp),
            movieItem = Movie(1, IMAGES_BASE_PATH + "vccE9bBa9mgghFpkWzU1fQqmOKB.jpg"),
        ) {

        }
    }
}
package com.loc.moviesfinder.core_feature.presentation.image_viewer_screen

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.loc.moviesfinder.R

@Composable
fun ImageViewerScreen(
    imagePath: String,
    type: ImageType,
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(Color.Black)

    var dotsVisibility by remember {
        mutableStateOf(true)
    }

    var expandDropDownMenu by remember {
        mutableStateOf(false)
    }


    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)
        .clickable(MutableInteractionSource(), null, true, onClick = {
            dotsVisibility = !dotsVisibility
        })
    ) {
        Column(
            modifier = Modifier
                .size(50.dp)
                .align(TopEnd)
                .padding(end = 24.dp, top = 24.dp),
        ) {

            AnimatedVisibility(visible = dotsVisibility,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                IconButton(onClick = { expandDropDownMenu = !expandDropDownMenu },
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(painter = painterResource(id = R.drawable.ic_vertical_dots),
                        contentDescription = stringResource(id = R.string.options),
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
            if (expandDropDownMenu) {
                DropdownMenu(expanded = expandDropDownMenu,
                    onDismissRequest = { expandDropDownMenu = false },
                    modifier = Modifier.background(Color.White)
                ) {

                    DropdownMenuItem(onClick = {
                        expandDropDownMenu = false
                        /*TODO: Call saveImage from viewModel*/
                    }, Modifier.background(Color.White)) {
                        Text(text = stringResource(id = R.string.save))
                    }
                }
            }
        }

        val imageRequest =
            ImageRequest.Builder(LocalContext.current).placeholder(R.drawable.loading)
                .error(R.drawable.error_icon).crossfade(true).data(
                    "https://i.ibb.co/KXr4g4t/2022-0701-01410200.jpg"
                ).build()
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(if (type == ImageType.BACKDROP) 16 / 9f else 3 / 4f)
                .align(Center),
            model = imageRequest,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            onSuccess = {

            },
        )
    }
}

enum class ImageType {
    BACKDROP, POSTER
}
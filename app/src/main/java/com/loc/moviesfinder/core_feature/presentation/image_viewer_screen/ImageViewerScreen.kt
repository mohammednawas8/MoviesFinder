package com.loc.moviesfinder.core_feature.presentation.image_viewer_screen

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
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
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.loc.moviesfinder.R
import com.loc.moviesfinder.core_feature.presentation.util.Constants.IMAGES_BASE_PATH


@Composable
fun ImageViewerScreen(
    imagePath: String,
    type: ImageType,
    viewModel: ImageDownloaderViewModel = hiltViewModel(),
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(Color.Black)

    var dotsVisibility by remember {
        mutableStateOf(true)
    }

    var expandDropDownMenu by remember {
        mutableStateOf(false)
    }

    val state = viewModel.downloadState.collectAsState().value


    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)
        .clickable(MutableInteractionSource(), null, true, onClick = {
            dotsVisibility = !dotsVisibility
        })
    ) {

        if (state.success) {
            Toast.makeText(LocalContext.current,
                stringResource(id = R.string.image_saved),
                Toast.LENGTH_LONG).show()
        }

        if (state.loading) {
            DownloadProgressSection()
        }

        if (state.error != null) {
            Toast.makeText(LocalContext.current,
                stringResource(id = R.string.image_unsaved),
                Toast.LENGTH_LONG).show()
        }

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
                        viewModel.downloadImage(imagePath)
                    }, Modifier.background(Color.White)) {
                        Text(text = stringResource(id = R.string.save))
                    }
                }
            }
        }

        val imageRequest =
            ImageRequest.Builder(LocalContext.current).placeholder(R.drawable.loading)
                .error(R.drawable.error_icon).crossfade(true).data(
                    IMAGES_BASE_PATH + imagePath
                ).build()
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(if (type == ImageType.BACKDROP) 16 / 9f else 3 / 4f)
                .align(Center),
            model = imageRequest,
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
    }
}


@Composable
private fun DownloadProgressSection() {
    LinearProgressIndicator(modifier = Modifier
        .fillMaxWidth()
        .height(2.dp))
}

enum class ImageType {
    BACKDROP, POSTER
}
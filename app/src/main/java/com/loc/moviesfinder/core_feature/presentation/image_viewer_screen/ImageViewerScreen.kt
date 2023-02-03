package com.loc.moviesfinder.core_feature.presentation.image_viewer_screen

import android.Manifest
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
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.loc.moviesfinder.R
import com.loc.moviesfinder.core_feature.presentation.util.Constants.IMAGES_BASE_PATH
import com.loc.moviesfinder.ui.theme.White200


@OptIn(ExperimentalPermissionsApi::class)
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

    val permissionsState = rememberMultiplePermissionsState(permissions = listOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
    ))

    var showPermissionRational by remember {
        mutableStateOf(false)
    }

    var showPermissionDenied by remember {
        mutableStateOf(false)
    }

    val state = viewModel.downloadState.collectAsState().value

    val context = LocalContext.current

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)
        .clickable(MutableInteractionSource(), null, true, onClick = {
            dotsVisibility = !dotsVisibility
        })
    ) {

        if (state.loading) {
            DownloadProgressSection()
        }

        //Message observer
        LaunchedEffect(key1 = true) {
            viewModel.message.collect {
                Toast.makeText(context,
                    it,
                    Toast.LENGTH_LONG).show()
            }
        }

        if (showPermissionRational) {
            RationalDialog(title = stringResource(id = R.string.rational_title),
                text = stringResource(
                    id = R.string.rational_text),
                ) {
                showPermissionRational = false
            }
        }
        if (showPermissionDenied) {
            RationalDialog(title = stringResource(id = R.string.permission_denied_title),
                text = stringResource(
                    id = R.string.permission_denied_text),
                ) {
                showPermissionRational = false
            }
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
                        if (sdk29AndUp()){
                            viewModel.downloadImage(imagePath)
                        }else{
                            permissionsState.launchMultiplePermissionRequest()
                            permissionsState.permissions.forEach { perm ->
                                when (perm.permission) {
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE -> {
                                        when {
                                            perm.hasPermission -> {
                                                viewModel.downloadImage(imagePath)
                                            }
                                            perm.shouldShowRationale -> {
                                                showPermissionRational = true
                                            }
                                            !perm.isPermanentlyDenied() -> {
                                                showPermissionDenied = true
                                            }
                                        }
                                    }
                                }
                            }
                        }

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
            contentScale = ContentScale.FillBounds,
        )
    }
}

@Composable
private fun
        RationalDialog(
    title: String,
    text: String,
    onDismissRequest: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        buttons = {
            TextButton(onClick = onDismissRequest) {
                Text(text = stringResource(id = R.string.ok), color = Color.Black)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        title = {
            Text(text = title, color = White200)
        },
        text = {
            Text(text = text,color = White200)
        }
    )
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
package com.loc.moviesfinder.core_feature.presentation.splash_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.loc.moviesfinder.R
import com.loc.moviesfinder.ui.theme.MoviesFinderTheme

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    homeNavigation: () -> Unit,
) {
    var isImageVisible by remember {
        mutableStateOf(false)
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        AnimatedVisibility(
            visible = isImageVisible,
            enter = fadeIn(animationSpec = tween(1200))
        ) {
            Image(painter = painterResource(id = R.drawable.popcorn),
                contentDescription = "popcorn",
                modifier = Modifier.size(189.dp)
            )
        }
    }
    LaunchedEffect(key1 = true) {
        isImageVisible = true
    }

    LaunchedEffect(key1 = true) {
        viewModel.navigate.collect { rout ->
            homeNavigation()
        }
    }

}

@Preview
@Composable
fun SplashScreenPreview() {
    MoviesFinderTheme {
        SplashScreen(){}
    }
}
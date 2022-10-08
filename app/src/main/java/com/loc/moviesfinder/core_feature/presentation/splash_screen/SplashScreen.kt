package com.loc.moviesfinder.core_feature.presentation.splash_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.loc.moviesfinder.R
import com.loc.moviesfinder.ui.theme.MoviesFinderTheme

@Composable
fun SplashScreen(
    navController: NavController,
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(painter = painterResource(id = R.drawable.popcorn),
            contentDescription = "popcorn",
            modifier = Modifier.size(189.dp)
        )
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    MoviesFinderTheme {
        SplashScreen(navController = rememberNavController())
    }
}
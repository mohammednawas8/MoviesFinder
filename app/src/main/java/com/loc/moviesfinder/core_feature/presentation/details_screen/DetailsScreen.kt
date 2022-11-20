package com.loc.moviesfinder.core_feature.presentation.details_screen

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.loc.moviesfinder.R
import com.loc.moviesfinder.core_feature.presentation.util.components.ScreenToolBar

@Composable
fun DetailsScreen(
    movieId: Int,
    viewModel: DetailsViewModel = hiltViewModel(),
    navController: NavController
) {


    LaunchedEffect(key1 = true) {
        viewModel.getMovieDetails(movieId)
//        TODO: call viewModel function to get the movie detail
    }

    ScreenToolBar(title = stringResource(id = R.string.detail),
        navigateBackRequest = { navController.navigateUp() },
        icon = painterResource(id = R.drawable.ic_unfilled_bookmark)
    )
}
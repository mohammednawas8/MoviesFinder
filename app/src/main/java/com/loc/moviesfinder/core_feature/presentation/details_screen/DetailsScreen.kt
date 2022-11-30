package com.loc.moviesfinder.core_feature.presentation.details_screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Bottom
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.loc.moviesfinder.R
import com.loc.moviesfinder.core_feature.domain.model.MovieDetails
import com.loc.moviesfinder.core_feature.presentation.details_screen.components.VerticalLine
import com.loc.moviesfinder.core_feature.presentation.home_screen.components.MovieCard
import com.loc.moviesfinder.core_feature.presentation.util.components.*
import com.loc.moviesfinder.core_feature.presentation.util.toSingleLine
import com.loc.moviesfinder.ui.theme.Black800
import com.loc.moviesfinder.ui.theme.Gray500
import com.loc.moviesfinder.ui.theme.Orange

@Composable
fun DetailsScreen(
    movieId: Int,
    viewModel: DetailsViewModel = hiltViewModel(),
    navController: NavController,
) {

    val state = viewModel.movieDetails.collectAsState().value

    LaunchedEffect(key1 = true) {
        viewModel.getMovieDetails(634649)
//        TODO: call viewModel function to get the movie detail
    }

    Column {
        Spacer(modifier = Modifier.height(20.dp))
        ScreenToolBar(
            title = stringResource(id = R.string.detail),
            navigateBackRequest = { navController.navigateUp() },
            icon = painterResource(id = if (state.isSaved) R.drawable.ic_filled_bookmark else R.drawable.ic_unfilled_bookmark),
            modifier = Modifier
                .fillMaxWidth()
                .height(36.dp)
                .padding(horizontal = 22.dp),
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (state.movieDetails == null) {

        } else {
            val movieDetailsTabs = rememberMovieDetailsTabs()

            BackdropPosterTitleSection(state.movieDetails)
            Spacer(modifier = Modifier.height(16.dp))
            MovieInformationSection(state.movieDetails)
            Spacer(modifier = Modifier.height(24.dp))

            var movieDetailsSection by remember {
                mutableStateOf(movieDetailsTabs[0])
            }
            MovieDetailsTabLayout(tabs = movieDetailsTabs, onTabSelect = { tab ->
                movieDetailsSection = tab
            })
            Spacer(modifier = Modifier.height(24.dp))
            when (movieDetailsSection) {
                is MovieDetailsTab.AboutMovie -> {
                    Text(text = state.movieDetails.aboutMovie,
                        style = MaterialTheme.typography.h5.copy(color = Color.White,
                            fontSize = 12.sp), modifier = Modifier.padding(horizontal = 25.dp))

                }
                is MovieDetailsTab.Reviews -> {

                }
                is MovieDetailsTab.Cast -> {

                }
            }
        }
    }
}

@Composable
private fun MovieInformationSection(movieDetails: MovieDetails) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 51.dp),
        horizontalArrangement = Arrangement.Center) {
        TextIcon(
            painter = painterResource(id = R.drawable.ic_calendar),
            text = movieDetails.releaseYear.toString(),
            color = Gray500
        )
        Spacer(modifier = Modifier.width(12.dp))
        VerticalLine()
        Spacer(modifier = Modifier.width(12.dp))
        TextIcon(
            painter = painterResource(id = R.drawable.ic_clock),
            text = "${movieDetails.durationInMinutes} ${stringResource(id = R.string.minutes)}",
            color = Gray500
        )
        Spacer(modifier = Modifier.width(12.dp))
        VerticalLine()
        Spacer(modifier = Modifier.width(12.dp))
        TextIcon(
            painter = painterResource(id = R.drawable.ic_ticket),
            text = movieDetails.genre[0],
            color = Gray500
        )
    }
}

@Composable
private fun BackdropPosterTitleSection(movieDetails: MovieDetails) {
    Box(modifier = Modifier
        .height(271.dp)
        .fillMaxWidth()) {

        //For backdrop image
        MovieBackDrop(averageRating = movieDetails.averageVoting,
            backdropPath = movieDetails.backdropPath,
            modifier = Modifier.height(210.dp))

        //For poster image and title
        Row(modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomStart)
            .padding(start = 29.dp, end = 29.dp), verticalAlignment = Bottom) {

            MovieImageCard(imageUrl = movieDetails.posterPath,
                modifier = Modifier
                    .height(140.dp)
                    .width(95.dp),
                shape = RoundedCornerShape(16.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))

            //For title
            Text(
                text = movieDetails.title,
                style = MaterialTheme.typography.h4,
                maxLines = 2,
            )
        }
    }
}

@Composable
fun MovieBackDrop(
    modifier: Modifier = Modifier,
    averageRating: Double,
    backdropPath: String?,
) {
    Box(modifier = modifier.clip(RoundedCornerShape(bottomEnd = 16.dp,
        bottomStart = 16.dp))) {
        MovieImageCard(imageUrl = backdropPath ?: "",
            modifier = Modifier
                .fillMaxWidth()
                .height(210.dp),
            shape = RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp)
        )
        MovieRating(rating = averageRating,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 11.dp, bottom = 9.dp))

    }
}

@Composable
fun MovieRating(rating: Double, modifier: Modifier) {
    Box(modifier = modifier
        .width(54.dp)
        .height(24.dp),
        contentAlignment = Alignment.Center

    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(8.dp))
            .background(Black800)
            .alpha(0.32f)
        )
        TextIcon(painter = painterResource(id = R.drawable.ic_star),
            contentDescription = stringResource(id = R.string.movie_rating),
            text = rating.toString(),
            color = Orange
        )
    }
}

@Composable
fun rememberMovieDetailsTabs(): List<MovieDetailsTab> {
    return listOf(
        MovieDetailsTab.AboutMovie(stringResource(id = R.string.about_movie)),
        MovieDetailsTab.Reviews(stringResource(id = R.string.reviews)),
        MovieDetailsTab.Cast(stringResource(id = R.string.cast)),
    )
}
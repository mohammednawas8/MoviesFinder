package com.loc.moviesfinder.core_feature.presentation.details_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
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
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.loc.moviesfinder.R
import com.loc.moviesfinder.core_feature.domain.model.Cast
import com.loc.moviesfinder.core_feature.domain.model.MovieDetails
import com.loc.moviesfinder.core_feature.domain.model.Review
import com.loc.moviesfinder.core_feature.presentation.details_screen.components.CastCard
import com.loc.moviesfinder.core_feature.presentation.details_screen.components.ReviewCard
import com.loc.moviesfinder.core_feature.presentation.details_screen.components.VerticalLine
import com.loc.moviesfinder.core_feature.presentation.image_viewer_screen.ImageType
import com.loc.moviesfinder.core_feature.presentation.util.components.*
import com.loc.moviesfinder.ui.theme.Black800
import com.loc.moviesfinder.ui.theme.Gray500
import com.loc.moviesfinder.ui.theme.Orange

@Composable
fun DetailsScreen(
    movieId: Int,
    viewModel: DetailsViewModel = hiltViewModel(),
    navController: NavController,
    onImageClick: (String?, ImageType) -> Unit,
) {
    val reviews = viewModel.reviewsPaginator.collectAsLazyPagingItems()
    val state = viewModel.movieDetails.collectAsState().value

    val reviewScrollState = rememberLazyListState()
    val castScrollState = rememberLazyGridState()

    LaunchedEffect(key1 = true) {
            viewModel.getMovie(movieId)
    }

    val moviesDetails = state.movieDetails

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
            iconClick = {
                viewModel.insertDeleteMovie()
            }
        )

        if (state.detailsLoading) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (moviesDetails == null) {
            //Impossible
        } else {
            val movieDetailsTabs = rememberMovieDetailsTabs()
            BackdropPosterTitleSection(moviesDetails, onImageClick = onImageClick)
            Spacer(modifier = Modifier.height(16.dp))
            MovieInformationSection(moviesDetails, state.genreString)
            Spacer(modifier = Modifier.height(24.dp))

            var movieDetailsSection by remember {
                mutableStateOf(movieDetailsTabs[0])
            }
            MovieDetailsTabLayout(tabs = movieDetailsTabs, onTabSelect = { tab ->
                movieDetailsSection = tab
                viewModel.reviewScrollPosition = reviewScrollState.firstVisibleItemScrollOffset
            })
            Spacer(modifier = Modifier.height(24.dp))

            //Save the scroll state in viewModel
            viewModel.castScrollPosition = castScrollState.firstVisibleItemIndex
            viewModel.reviewScrollPosition = reviewScrollState.firstVisibleItemIndex
            when (movieDetailsSection) {
                is MovieDetailsTab.AboutMovie -> {
                    AboutMovieSection(state.movieDetails)
                }
                is MovieDetailsTab.Reviews -> {
                    ReviewsSection(reviews, reviewScrollState, viewModel.reviewScrollPosition)
                }
                is MovieDetailsTab.Cast -> {
                    CastSection(state.castList, castScrollState, viewModel.castScrollPosition)
                }
            }
        }
    }
}

@Composable
private fun CastSection(
    castList: List<Cast>,
    scrollState: LazyGridState,
    scrollIndex: Int,
) {
    LaunchedEffect(key1 = true) {
        scrollState.scrollToItem(index = scrollIndex)
    }
    LazyVerticalGrid(columns = GridCells.Fixed(2), state = scrollState,
        contentPadding = PaddingValues(bottom = 10.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)) {
        itemsIndexed(castList) { _, item ->
            CastCard(cast = item)
        }
    }
}

@Composable
private fun AboutMovieSection(movieDetails: MovieDetails) {
    Text(text = movieDetails.aboutMovie,
        style = MaterialTheme.typography.h5.copy(color = Color.White,
            fontSize = 12.sp), modifier = Modifier.padding(horizontal = 25.dp))
}

@Composable
private fun ReviewsSection(
    reviews: LazyPagingItems<Review>,
    scrollState: LazyListState,
    scrollIndex: Int,
) {
    LaunchedEffect(key1 = true) {
        scrollState.scrollToItem(scrollIndex)
    }
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 10.dp),
        state = scrollState
    ) {
        itemsIndexed(reviews) { _, review ->
            review?.let {
                ReviewCard(review = it,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 34.dp))
            }

        }
    }
}

@Composable
@Stable
private fun MovieInformationSection(movieDetails: MovieDetails, genreString: String) {
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
            text = genreString,
            color = Gray500
        )
    }
}

@Composable
private fun BackdropPosterTitleSection(
    movieDetails: MovieDetails,
    onImageClick: (String?, ImageType) -> Unit,
) {
    Box(modifier = Modifier
        .height(271.dp)
        .fillMaxWidth()) {

        //For backdrop image
        MovieBackDrop(averageRating = movieDetails.averageVoting,
            backdropPath = movieDetails.backdropPath,
            modifier = Modifier
                .height(210.dp), onImageClick = onImageClick)

        //For poster image and title
        Row(modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomStart)
            .padding(start = 29.dp, end = 29.dp), verticalAlignment = Bottom) {

            MovieImageCard(
                imageUrl = movieDetails.posterPath,
                modifier = Modifier
                    .height(140.dp)
                    .width(95.dp)
                    .clickable { onImageClick(movieDetails.posterPath, ImageType.POSTER) },
                shape = RoundedCornerShape(16.dp),
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
    onImageClick: (String?, ImageType) -> Unit,
) {
    Box(modifier = modifier.clip(RoundedCornerShape(bottomEnd = 16.dp,
        bottomStart = 16.dp))) {
        MovieImageCard(imageUrl = backdropPath ?: "",
            modifier = Modifier
                .fillMaxWidth()
                .height(210.dp)
                .clickable { onImageClick(backdropPath, ImageType.BACKDROP) },
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
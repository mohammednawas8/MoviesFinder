package com.loc.moviesfinder.core_feature.presentation.home_screen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.loc.moviesfinder.R
import com.loc.moviesfinder.core_feature.domain.model.Movie
import androidx.paging.CombinedLoadStates
import com.loc.moviesfinder.core_feature.domain.util.MoviesGenre
import com.loc.moviesfinder.core_feature.presentation.home_screen.components.*
import com.loc.moviesfinder.core_feature.presentation.util.components.EditableSearchbar
import com.loc.moviesfinder.core_feature.presentation.util.components.gridItems
import com.loc.moviesfinder.ui.theme.Gray600
import com.loc.moviesfinder.ui.theme.MoviesFinderTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
) {


    val trendingMovies = viewModel.trendingMovies.collectAsLazyPagingItems()
    val tabLayoutSectionState = viewModel.tabLayoutMoviesState.collectAsState().value

    var selectedTab by remember {
        mutableStateOf(MoviesGenre.NOW_PLAYING)
    }

    val tabLayoutMovies = derivedStateOf {
        when (selectedTab) {
            MoviesGenre.NOW_PLAYING -> tabLayoutSectionState.nowPlayingMovies
            MoviesGenre.POPULAR -> tabLayoutSectionState.popularMovies
            MoviesGenre.LATEST -> tabLayoutSectionState.latestMovies
            MoviesGenre.UPCOMING -> tabLayoutSectionState.upcomingMovies
            MoviesGenre.TOP_RATED -> tabLayoutSectionState.topRatedMovies
            else -> emptyList()
        }
    }.value

    val moviesCategories = rememberMoviesCategories()
    val lazyState = rememberLazyListState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        state = lazyState
    ) {
        item {
            Spacer(modifier = Modifier.height(10.dp))

            HeaderText(modifier = Modifier.padding(horizontal = 24.dp))

            Spacer(modifier = Modifier.height(24.dp))

            EditableSearchbar(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth()
                    .height(42.dp),
                editable = true) {

            }

            Spacer(modifier = Modifier.height(20.dp))

            TrendingMoviesSection(trendingMovies)

            Spacer(modifier = Modifier.height(24.dp))

            MoviesTabLayout(moviesCategories) {
                selectedTab = it.tab
            }

            Spacer(modifier = Modifier.height(20.dp))
        }

        gridItems(
            tabLayoutMovies,
            3,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalSpace = 18.dp,
            horizontalArrangement = Arrangement.SpaceAround,
            lastItemReached = {
                Log.d("test", "paging request")
                viewModel.loadNowPlayingMovies()
            }
        ) { movie ->
            MovieCard(movieItem = movie, modifier = Modifier
                .width(100.dp)
                .height(146.dp)
                .padding(horizontal = 11.dp)
            ) {
            }
        }
    }
}

@Composable
private fun TrendingMoviesSection(trendingMovies: LazyPagingItems<Movie>) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(250.dp)
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            contentPadding = PaddingValues(start = 24.dp),
            modifier = Modifier
                .fillMaxSize()
        ) {
            itemsIndexed(trendingMovies) { i, movie ->
                movie?.let {
                    TrendingMovieCard(
                        number = i + 1,
                        movieItem = movie
                    ) {

                    }

                }
            }
        }
        val loadState = trendingMovies.loadState
        MoviesLoadState(loadState, trendingMovies)
    }
}


@Composable
private fun MoviesLoadState(
    loadState: CombinedLoadStates,
    moviesPagingItems: LazyPagingItems<Movie>,
) {
    when {
        //Initial Loading
        loadState.refresh is LoadState.Loading -> {
            CircularLoading()
        }

        //Loading new items
        loadState.append is LoadState.Loading -> {
        }

        loadState.refresh is LoadState.Error -> {
            val e = loadState.refresh as LoadState.Error
            Retry(e) {
                moviesPagingItems.retry()
            }
        }

        loadState.append is LoadState.Error -> {

        }
    }
}
@Composable
private fun CircularLoading() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            color = Gray600,
            modifier = Modifier.size(32.dp)
        )
    }
}


@Composable
private fun Retry(
    e: LoadState.Error? = null,
    retryRequest: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        RetryItem(modifier = Modifier
            .width(90.dp)
            .height(40.dp),
            e?.error?.message.toString()
        ) {
            retryRequest()
        }
    }
}

@Composable
fun HeaderText(
    modifier: Modifier = Modifier,
    text: String = stringResource(id = R.string.home_screen_header),
) {
    Text(
        text = text,
        style = MaterialTheme.typography.h4,
        modifier = modifier,
        color = Color.White
    )
}

@Composable
fun rememberMoviesCategories(): List<MoviesTab> {
    return listOf(
        MoviesTab(stringResource(id = R.string.now_playing),
            MoviesGenre.NOW_PLAYING),
        MoviesTab(stringResource(id = R.string.upcoming), MoviesGenre.UPCOMING),
        MoviesTab(stringResource(id = R.string.top_rated), MoviesGenre.TOP_RATED),
        MoviesTab(stringResource(id = R.string.popular), MoviesGenre.POPULAR),
        MoviesTab(stringResource(id = R.string.latest), MoviesGenre.LATEST),
    )
}


@Preview
@Composable
fun HeaderTextPreview() {
    MoviesFinderTheme {
        HeaderText()
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    MoviesFinderTheme {
        HomeScreen(navController = rememberNavController())
    }
}
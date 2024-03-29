package com.loc.moviesfinder.core_feature.presentation.home_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.loc.moviesfinder.R
import com.loc.moviesfinder.core_feature.domain.model.Movie
import com.loc.moviesfinder.core_feature.domain.util.MoviesGenre
import com.loc.moviesfinder.core_feature.presentation.home_screen.components.MovieCard
import com.loc.moviesfinder.core_feature.presentation.home_screen.components.RetryItem
import com.loc.moviesfinder.core_feature.presentation.home_screen.components.TrendingMovieCard
import com.loc.moviesfinder.core_feature.presentation.util.components.MoviesTabLayout
import com.loc.moviesfinder.core_feature.presentation.util.components.UnEditableSearchbar
import com.loc.moviesfinder.core_feature.presentation.util.components.gridItems
import com.loc.moviesfinder.ui.theme.Gray600
import com.loc.moviesfinder.ui.theme.MoviesFinderTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToSearch: () -> Unit,
    navigateToMovie: (Movie) -> Unit,
) {


    val trendingMovies = viewModel.trendingMovies.collectAsLazyPagingItems()
    val tabLayoutSectionState = viewModel.tabLayoutMoviesState.collectAsState().value

    val tabLayoutMovies = tabLayoutSectionState.tabLayoutMovies

    val selectedTabIndex = tabLayoutSectionState.selectedIndex

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

            UnEditableSearchbar(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth()
                    .height(42.dp),
                onClick = {
                    navigateToSearch()
                })

            Spacer(modifier = Modifier.height(20.dp))

            TrendingMoviesSection(trendingMovies,viewModel) {
                navigateToMovie(it)
            }


            Spacer(modifier = Modifier.height(24.dp))

            MoviesTabLayout(moviesCategories, selectedTabIndex) {
                viewModel.changeMoviesTab(it.tab)
            }

            if (tabLayoutSectionState.isLoading)
                LinearProgressIndicator(modifier = Modifier
                    .padding(top = 5.dp)
                    .fillMaxWidth()
                    .height(1.dp))

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
                viewModel.loadNextPage()
            }
        ) { movie ->
            MovieCard(movieItem = movie, modifier = Modifier
                .width(100.dp)
                .height(146.dp)
                .padding(horizontal = 11.dp)
            ) {
                navigateToMovie(it)
            }
        }
    }
}

@Composable
private fun TrendingMoviesSection(
    trendingMovies: LazyPagingItems<Movie>,
    viewModel: HomeViewModel,
    onClick: (Movie) -> Unit,
) {
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
                        onClick(it)
                    }

                }
            }
        }
        val loadState = trendingMovies.loadState
        MoviesLoadState(loadState, trendingMovies,viewModel)
    }
}


@Composable
private fun MoviesLoadState(
    loadState: CombinedLoadStates,
    moviesPagingItems: LazyPagingItems<Movie>,
    viewModel: HomeViewModel
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
        HomeScreen(
            navigateToSearch = {}) {}
    }
}
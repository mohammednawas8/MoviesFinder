package com.loc.moviesfinder.core_feature.presentation.search_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.loc.moviesfinder.R
import com.loc.moviesfinder.core_feature.domain.model.MovieDetails
import com.loc.moviesfinder.core_feature.presentation.util.components.*
import com.loc.moviesfinder.ui.theme.Gray600

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    focus: Boolean,
    navigateToHome: () -> Unit,
    onClick: (MovieDetails) -> Unit,
) {
    val state = viewModel.searchState.collectAsState()
    var showSearchDialog by remember {
        mutableStateOf(false)
    }

    if (state.value.isEmpty && !state.value.pagingLoading && !state.value.newSearchLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            NoResultBox(
                image = painterResource(id = R.drawable.ic_no_searcb_results),
                title = stringResource(id = R.string.no_search_results),
                subTitle = stringResource(id = R.string.no_search_results_description),
            )
        }
    }


    Column(modifier = Modifier.fillMaxSize()) {
        val focusRequester = remember {
            FocusRequester()
        }
        Spacer(modifier = Modifier.height(20.dp))

        ScreenToolBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(42.dp)
                .padding(horizontal = 22.dp),
            title = stringResource(id = R.string.search),
            icon = painterResource(id = R.drawable.ic_info_circle),
            navigateBackRequest = {
                navigateToHome()
            },
            iconClick = {
                showSearchDialog = true
            }
        )

        if (showSearchDialog)
            SearchDialog {
                showSearchDialog = false
            }

        Spacer(modifier = Modifier.height(36.dp))

        EditableSearchbar(
            modifier = Modifier
                .padding(horizontal = 29.dp)
                .fillMaxWidth()
                .height(42.dp),
            focusRequester = focusRequester,
        ) {
            viewModel.searchMovies(it)
        }

            if (focus)
                LaunchedEffect(key1 = true, block = { focusRequester.requestFocus() })

        Spacer(modifier = Modifier.height(24.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(24.dp),
            contentPadding = PaddingValues(bottom = 24.dp, start = 29.dp, end = 29.dp)) {

            if (state.value.newSearchLoading)
                items(20) {
                    MovieDetailsShimmerCard(modifier = Modifier.fillMaxSize())
                }

            itemsIndexed(state.value.searchedMovies) { index, item ->
                if (index == state.value.searchedMovies.size - 1) {
                    viewModel.loadNextMovies()
                }
                MovieDetailsCard(movie = item) {
                    onClick(it)
                }
            }

            if (state.value.pagingLoading) {
                item {
                    CircularLoading()
                }
            }

        }
    }
}

@Composable
fun SearchDialog(
    onDismissRequest: () -> Unit,
) {

    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = { onDismissRequest() }) {
                Text(text = stringResource(id = R.string.ok))
            }
        },
        title = {
            Text(text = stringResource(id = R.string.search),
                style = MaterialTheme.typography.h5.copy(
                    Color.White, fontSize = 16.sp))
        },
        text = {
            Text(text = stringResource(id = R.string.search_text),
                style = MaterialTheme.typography.h6.copy(color = Gray600, fontSize = 14.sp))
        }
    )
}

@Composable
private fun CircularLoading() {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(bottom = 32.dp), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            color = Gray600,
            modifier = Modifier.size(32.dp)
        )
    }
}
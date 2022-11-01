package com.loc.moviesfinder.core_feature.presentation.search_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.loc.moviesfinder.core_feature.presentation.util.components.EditableSearchbar
import com.loc.moviesfinder.core_feature.presentation.util.components.ScreenToolBar
import com.loc.moviesfinder.R
import com.loc.moviesfinder.core_feature.presentation.search_screen.components.SearchedItemCard

@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val state = viewModel.searchState.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(20.dp))

        ScreenToolBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(42.dp)
                .padding(horizontal = 22.dp),
            title = stringResource(id = R.string.search),
            icon = painterResource(id = R.drawable.ic_info_circle),
            navigateBackRequest = {

            },
            iconClick = {
                navController.navigateUp()
            }
        )

        Spacer(modifier = Modifier.height(36.dp))

        EditableSearchbar(
            modifier = Modifier
                .padding(horizontal = 29.dp)
                .fillMaxWidth()
                .height(42.dp),
            editable = true
        ) {
            viewModel.searchMovies(it)
        }

        Spacer(modifier = Modifier.height(24.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(24.dp),
            contentPadding = PaddingValues(bottom = 24.dp, start = 29.dp, end = 29.dp)) {
            itemsIndexed(state.value.searchedMovies) { index, item ->
                SearchedItemCard(movie = item) {

                }
            }
        }


    }

}
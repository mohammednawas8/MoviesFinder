package com.loc.moviesfinder.core_feature.presentation.watch_list_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.loc.moviesfinder.R
import com.loc.moviesfinder.core_feature.domain.model.Movie
import com.loc.moviesfinder.core_feature.domain.model.MovieDetails
import com.loc.moviesfinder.core_feature.presentation.util.components.MovieDetailsCard
import com.loc.moviesfinder.core_feature.presentation.util.components.MovieDetailsShimmerCard
import com.loc.moviesfinder.core_feature.presentation.util.components.NoResultBox
import com.loc.moviesfinder.core_feature.presentation.util.components.ScreenToolBar

@Composable
fun WatchListScreen(
    navController: NavController,
    viewModel: WatchListViewModel = hiltViewModel(),
    onClick:(MovieDetails) -> Unit
) {

    val state = viewModel.state.collectAsState().value

    Box(modifier = Modifier.fillMaxSize()) {

        if (state.empty){
            NoResultBox(image = painterResource(id = R.drawable.empty_box), title = stringResource(
                id = R.string.no_movie_title) , subTitle = stringResource(id = R.string.no_movie_description),
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(20.dp))

            ScreenToolBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(42.dp)
                    .padding(horizontal = 22.dp),
                title = stringResource(id = R.string.watch_list),
                navigateBackRequest = {
                    navController.navigateUp()
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(24.dp),
                contentPadding = PaddingValues(bottom = 24.dp, start = 29.dp, end = 29.dp)) {


                itemsIndexed(state.moviesList) { index, item ->
                    MovieDetailsCard(movie = item) {
                        onClick(it)
                    }
                }
            }
        }


    }

}
package com.loc.moviesfinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.loc.moviesfinder.core_feature.presentation.home_screen.HomeViewModel
import com.loc.moviesfinder.core_feature.presentation.navigation.NavigationScreen
import com.loc.moviesfinder.core_feature.presentation.search_screen.SearchViewModel
import com.loc.moviesfinder.core_feature.presentation.watch_list_screen.WatchListViewModel
import com.loc.moviesfinder.ui.theme.MoviesFinderTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoviesFinderTheme {

                val homeViewModel: HomeViewModel = viewModel()
                val searchViewModel: SearchViewModel = viewModel()
                val watchListViewModel: WatchListViewModel = viewModel()

                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NavigationScreen(homeViewModel,searchViewModel,watchListViewModel)
                }
            }
        }
    }
}


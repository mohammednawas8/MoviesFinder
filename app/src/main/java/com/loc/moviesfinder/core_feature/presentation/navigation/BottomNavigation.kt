package com.loc.moviesfinder.core_feature.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.loc.moviesfinder.ui.theme.Gray600
import com.loc.moviesfinder.R
import com.loc.moviesfinder.ui.theme.BlueLight
import com.loc.moviesfinder.ui.theme.MoviesFinderTheme

@Composable
fun MoviesBottomNavigation(
    selectedItem: Navigation,
    onHomeClick: () -> Unit,
    onSearchClick: () -> Unit,
    onWatchListClick: () -> Unit,
) {

    BottomNavigation(modifier = Modifier
        .fillMaxWidth()
        .height(78.dp),
        backgroundColor = MaterialTheme.colors.background
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(BlueLight))
            Row(modifier = Modifier
                .fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {


                MoviesBottomNavigationItem(selectedItem = selectedItem == Navigation.HomeScreen, icon = painterResource(
                    id = R.drawable.ic_home), text = stringResource(id = R.string.home)) {
                    onHomeClick()
                }

                MoviesBottomNavigationItem(selectedItem = selectedItem == Navigation.SearchScreen, icon = painterResource(
                    id = R.drawable.ic_search), text = stringResource(id = R.string.search)) {
                    onSearchClick()
                }

                MoviesBottomNavigationItem(selectedItem = selectedItem == Navigation.WatchListScreen, icon = painterResource(
                    id = R.drawable.ic_save), text = stringResource(id = R.string.watch_list)) {
                    onWatchListClick()
                }

            }
        }


    }
}

@Composable
fun RowScope.MoviesBottomNavigationItem(
    selectedItem: Boolean,
    icon: Painter,
    text: String,
    onClick: () -> Unit,
) {
    BottomNavigationItem(
        selected = selectedItem,
        selectedContentColor = BlueLight,
        unselectedContentColor = Gray600,
        onClick = onClick,
        icon = {
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(5.dp)) {
                Icon(painter = icon, contentDescription = text,
                    modifier = Modifier
                        .width(21.dp)
                        .height(24.dp)
                )
                Text(text = text,
                    style = MaterialTheme.typography.h6,
                    maxLines = 1)
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
fun MoviesBottomNavigationPreview() {
    MoviesFinderTheme {
       MoviesBottomNavigation(selectedItem = Navigation.HomeScreen,
           onHomeClick = { /*TODO*/ },
           onSearchClick = { /*TODO*/ }) {

       }
    }
}
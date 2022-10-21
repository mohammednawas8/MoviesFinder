package com.loc.moviesfinder.core_feature.presentation.home_screen.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.loc.moviesfinder.core_feature.presentation.home_screen.MoviesTab

@Composable
fun MoviesTabLayout(
    tabs: List<MoviesTab>,
    onTabSelect: (MoviesTab) -> Unit,
) {
    var selectedIndex by remember {
        mutableStateOf(0)
    }

    ScrollableTabRow(
        selectedTabIndex = selectedIndex,
        backgroundColor = Color.Transparent,
        modifier = Modifier
            .height(41.dp),
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier
                    .tabIndicatorOffset(
                        currentTabPosition = tabPositions[selectedIndex],
                    )
                    .height(4.dp),
                color = MaterialTheme.colors.surface,
            )
        },
        divider = {},
        edgePadding = 24.dp,
    ) {
        tabs.forEachIndexed { index, moviesTab ->
            Tab(
                selected = selectedIndex == index,
                onClick = {
                    selectedIndex = index
                    onTabSelect(tabs[index])
                },
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(horizontal = 12.dp),
                selectedContentColor = Color.White,
                unselectedContentColor = Color.White,
            ) {
                Text(text = moviesTab.title,
                    style = MaterialTheme.typography.h5,
                    color = Color.White)
            }
        }
    }
}

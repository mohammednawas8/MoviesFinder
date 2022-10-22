package com.loc.moviesfinder.core_feature.presentation.search_screen

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.loc.moviesfinder.core_feature.presentation.util.components.EditableSearchbar
import com.loc.moviesfinder.core_feature.presentation.util.components.ScreenToolBar
import com.loc.moviesfinder.R

@Composable
fun SearchScreen(
    navController: NavController,
) {

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

        }

        Spacer(modifier = Modifier.height(24.dp))



    }

}
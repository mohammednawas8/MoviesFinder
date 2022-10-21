package com.loc.moviesfinder.core_feature.presentation.home_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.loc.moviesfinder.R
import com.loc.moviesfinder.ui.theme.Gray600
import com.loc.moviesfinder.ui.theme.MoviesFinderTheme

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Box(modifier = modifier
        .clip(MaterialTheme.shapes.large)
        .background(MaterialTheme.colors.surface)
        .clickable { onClick() }) {

        Row(modifier = Modifier
            .fillMaxSize()
            .padding(start = (24.7).dp, end = (17.78).dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {

            Text(
                text = stringResource(id = R.string.search),
                style = MaterialTheme.typography.h5,
                color = Gray600,
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "search",
                modifier = Modifier.size(16.dp),
                tint = Gray600)

        }

    }
}

@Preview
@Composable
fun SearchBarPreview() {
    MoviesFinderTheme {
        SearchBar(modifier = Modifier
            .fillMaxWidth()
            .height(42.dp)) {}
    }
}
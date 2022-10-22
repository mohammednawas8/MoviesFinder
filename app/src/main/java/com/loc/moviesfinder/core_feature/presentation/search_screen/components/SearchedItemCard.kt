package com.loc.moviesfinder.core_feature.presentation.search_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.loc.moviesfinder.core_feature.domain.model.Movie
import com.loc.moviesfinder.core_feature.presentation.home_screen.components.MovieCard
import com.loc.moviesfinder.core_feature.presentation.search_screen.SearchedMovie
import com.loc.moviesfinder.R
import com.loc.moviesfinder.ui.theme.MoviesFinderTheme
import com.loc.moviesfinder.ui.theme.Orange

@Composable
fun SearchedItemCard(
    modifier: Modifier = Modifier,
    movie: SearchedMovie,
    movieClick: (SearchedMovie) -> Unit,
) {
    Row(modifier = modifier
        .fillMaxWidth()
        .height(120.dp)
        .background(Color.Transparent)
        .clickable { movieClick(movie) },
        horizontalArrangement = Arrangement.spacedBy(12.dp)) {

        MovieCard(
            movieItem = Movie(movie.id, movie.coverPath),
            modifier = Modifier
                .fillMaxHeight()
                .width(95.dp)
        ) {
        }

        Column {
            Text(text = movie.title,
                style = MaterialTheme.typography.h5,
                color = Color.White,
                fontSize = 16.sp)
            Spacer(modifier = Modifier.height(14.dp))
            Column(modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween) {
                SearchedCardTextRow(
                    text = movie.voteAverage.toString(),
                    textStyle = MaterialTheme.typography.h6,
                    textColor = Orange,
                    icon = painterResource(id = R.drawable.ic_star),
                    iconTint = Orange
                )
                SearchedCardTextRow(
                    text = movie.category,
                    textStyle = MaterialTheme.typography.h6,
                    icon = painterResource(id = R.drawable.ic_ticket)
                )
                SearchedCardTextRow(
                    text = movie.year.toString(),
                    textStyle = MaterialTheme.typography.h6,
                    icon = painterResource(id = R.drawable.ic_calendar)
                )
                SearchedCardTextRow(
                    text = movie.durationInMinutes.toString(),
                    textStyle = MaterialTheme.typography.h6,
                    icon = painterResource(id = R.drawable.ic_clock)
                )
            }
        }

    }
}

@Composable
fun SearchedCardTextRow(
    text: String,
    textStyle: TextStyle,
    textColor: Color = Color.White,
    icon: Painter,
    iconTint: Color = Color.White,
) {
    Row {
        Icon(painter = icon,
            contentDescription = text,
            modifier = Modifier.size(16.dp),
            tint = iconTint)
        Spacer(modifier = Modifier.width(5.dp))
        Text(text = text, style = textStyle, color = textColor)
    }
}

@Preview
@Composable
fun SearchedItemCardPreview() {
    MoviesFinderTheme {
        SearchedItemCard(movie = SearchedMovie(1, "", "Spiderman", 9.5, "Action", 2019, 139),
            movieClick = {})
    }
}
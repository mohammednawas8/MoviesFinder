package com.loc.moviesfinder.core_feature.presentation.home_screen.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.loc.moviesfinder.R
import com.loc.moviesfinder.ui.theme.MoviesFinderTheme
import com.loc.moviesfinder.ui.theme.White200


@Composable
fun RetryItem(
    modifier: Modifier = Modifier,
    message: String?,
    onClick: () -> Unit,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(24.dp)) {
        Button(
            onClick = onClick,
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface,
                contentColor = MaterialTheme.colors.primary),
            modifier = modifier
        ) {
            Row {
                Text(
                    text = stringResource(id = R.string.retry),
                    style = MaterialTheme.typography.h4,
                    color = White200,
                )
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        message?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.h5,
                color = White200.copy(alpha = 0.8f),
            )
        }
    }
}

@Preview
@Composable
fun RetryItemPreview() {
    MoviesFinderTheme {
        RetryItem(modifier = Modifier
            .height(40.dp)
            .height(100.dp), "Check your connection") {}
    }
}
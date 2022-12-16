package com.loc.moviesfinder.core_feature.presentation.util.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.loc.moviesfinder.ui.theme.Gray500

@Composable
fun NoResultBox(
    modifier: Modifier = Modifier,
    image: Painter,
    title: String,
    subTitle: String,
) {

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,) {

            Image(painter = image, contentDescription = null, modifier = Modifier.size(76.dp))

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = title, style = MaterialTheme.typography.h3)

            Spacer(modifier = Modifier.height(15.dp))

            Text(text = subTitle, style = MaterialTheme.typography.h6.copy(Gray500))

        }
    }

}
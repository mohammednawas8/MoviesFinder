package com.loc.moviesfinder.core_feature.presentation.details_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.loc.moviesfinder.ui.theme.Gray500

@Composable
fun VerticalLine(
    modifier: Modifier = Modifier,
    width: Dp = 1.dp,
    height: Dp = 16.dp,
    color: Color = Gray500
) {
    Box(modifier = Modifier
        .width(1.dp)
        .height(height)
        .background(color))
}
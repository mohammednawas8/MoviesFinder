package com.loc.moviesfinder.core_feature.presentation.util.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import com.loc.moviesfinder.ui.theme.Gray600
import com.loc.moviesfinder.ui.theme.MoviesFinderTheme

@Composable
fun ShimmerCard(
    targetValue: Float = 1000f
) {

        val shimmerColors = listOf(
            MaterialTheme.colors.surface,
            Gray600,
            MaterialTheme.colors.surface,
        )

        val infiniteTransition = rememberInfiniteTransition()
        val endValue = infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = targetValue,
            animationSpec = InfiniteRepeatableSpec(
                tween(1000),
                RepeatMode.Reverse
            )
        )

        val brush = Brush.linearGradient(
            colors = shimmerColors,
            start = Offset.Zero,
            end = Offset(x = endValue.value, y = endValue.value)
        )

        Spacer(Modifier
            .fillMaxSize()
            .background(brush))

}

@Preview
@Composable
fun ShimmerCardPreview() {
    MoviesFinderTheme {
        ShimmerCard()
    }
}
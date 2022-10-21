package com.loc.moviesfinder.core_feature.presentation.home_screen.components

import android.os.Build
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.loc.moviesfinder.R
import com.loc.moviesfinder.core_feature.domain.model.Movie
import com.loc.moviesfinder.core_feature.presentation.util.Constants.IMAGES_BASE_PATH
import com.loc.moviesfinder.ui.theme.BlackPurple
import com.loc.moviesfinder.ui.theme.BlueLight
import com.loc.moviesfinder.ui.theme.MoviesFinderTheme

@Composable
fun TrendingMovieCard(
    number: Int,
    movieItem: Movie,
    onClick: (Movie) -> Unit,
) {
    val context = LocalContext.current
    Box(modifier = Modifier
        .width(154.dp)
        .height(226.dp)) {
        MovieCard(movieItem = movieItem, modifier = Modifier
            .width(144.dp)
            .height(210.dp)
            .align(Alignment.TopEnd)) {
            onClick(it)
        }

        val size =
            with(LocalDensity.current) {
                96.dp.toPx().toFloat()
            }

        val customTypeface = remember {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) context.resources.getFont(R.font.poppins_semibold) else null
        }

        val textPaintStroke = Paint().asFrameworkPaint().apply {
            isAntiAlias = true
            style = android.graphics.Paint.Style.STROKE
            textSize = size
            color = BlueLight.toArgb()
            strokeWidth = 1f
            strokeMiter = 10f
            strokeJoin = android.graphics.Paint.Join.ROUND
            typeface = customTypeface
        }

        val textPaint = Paint().asFrameworkPaint().apply {
            isAntiAlias = true
            style = android.graphics.Paint.Style.FILL
            textSize = size
            color = BlackPurple.toArgb()
            typeface = customTypeface
        }



        Canvas(modifier = Modifier.matchParentSize()) {
            drawIntoCanvas {
                it.nativeCanvas.drawText(
                    number.toString(),
                    0f,
                    this.size.height,
                    textPaint
                )

                it.nativeCanvas.drawText(
                    number.toString(),
                    0f,
                    this.size.height,
                    textPaintStroke
                )
            }
        }

    }
}

@Preview
@Composable
fun TrendingMovieCardPreview() {
    MoviesFinderTheme {
        TrendingMovieCard(number = 12,
            Movie(1, IMAGES_BASE_PATH + "92PJmMopfy64VYjd0HvIQaHGZX0.jpg")) {
        }
    }
}
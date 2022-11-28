package com.loc.moviesfinder.core_feature.presentation.util.components

import android.graphics.Paint
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.loc.moviesfinder.R
import com.loc.moviesfinder.ui.theme.Orange

@Composable
fun TextIcon(
    painter: Painter,
    text: String,
    space: Dp = 4.dp,
    textStyle: TextStyle = MaterialTheme.typography.h6,
    color: Color? = null,
    iconSize: Dp = 16.dp,
    contentDescription: String? = null,
) {
    Row {
        Icon(painter = painter,
            contentDescription = contentDescription,
            modifier = Modifier.size(16.dp),
            tint = color ?: Color.White)

        Spacer(modifier = Modifier.width(space))
        Text(text = text,
            style = textStyle,
            color = color ?: Color.White,
            maxLines = 1)
    }
}
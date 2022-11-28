package com.loc.moviesfinder.core_feature.presentation.util.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.loc.moviesfinder.R
import com.loc.moviesfinder.ui.theme.MontserratFont
import com.loc.moviesfinder.ui.theme.MoviesFinderTheme

@Composable
fun ScreenToolBar(
    modifier: Modifier = Modifier,
    title: String,
    icon: Painter? = null,
    iconWidth: Dp = 24.dp,
    navigateBackRequest: () -> Unit,
    iconClick: (() -> Unit)? = null,
) {
    Box(modifier = modifier) {
        Row(modifier = Modifier.matchParentSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {

            IconButton(onClick = navigateBackRequest) {
                Icon(painter = painterResource(id = R.drawable.ic_arrow_left),
                    contentDescription = "Navigate back",
                    modifier = Modifier.size(20.dp),
                    tint = Color.White
                )
            }


            Text(text = title, style = MaterialTheme.typography.h4, fontFamily = MontserratFont)

            if (icon == null) {
                Spacer(modifier = Modifier.size(20.dp))
            } else {
                IconButton(
                    onClick = { iconClick?.invoke() },
                    modifier = Modifier.width(iconWidth).fillMaxHeight(),
                    ) {
                    Icon(painter = icon,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ScreenToolBarPreview() {
    MoviesFinderTheme {
        ScreenToolBar(title = "Watch List",
            navigateBackRequest = { /*TODO*/ },
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .height(36.dp)
                .fillMaxWidth()
                .padding(horizontal =24.dp))
    }
}
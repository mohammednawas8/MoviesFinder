package com.loc.moviesfinder.core_feature.presentation.details_screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.loc.moviesfinder.core_feature.domain.model.Cast
import com.loc.moviesfinder.core_feature.presentation.util.components.ShimmerImageCard

@Composable
fun CastCard(
    cast: Cast,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        ShimmerImageCard(path = cast.profilePath ?: "",
            modifier = Modifier
                .size(100.dp).clip(CircleShape))
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = cast.name,
            style = MaterialTheme.typography.h5.copy(color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium))
    }
}
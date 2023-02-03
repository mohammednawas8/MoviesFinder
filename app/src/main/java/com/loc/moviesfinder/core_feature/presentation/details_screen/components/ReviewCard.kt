package com.loc.moviesfinder.core_feature.presentation.details_screen.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.loc.moviesfinder.R
import com.loc.moviesfinder.core_feature.domain.model.Review
import com.loc.moviesfinder.ui.theme.BlueLight

@Composable
fun ReviewCard(
    modifier: Modifier = Modifier,
    review: Review,
) {
    Box(modifier = modifier) {
        val context = LocalContext.current
        Row {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                AsyncImage(model = ImageRequest.Builder(context).crossfade(true)
                    .placeholder(R.drawable.reviewer)
                    .data(review.avatarPath).error(R.drawable.reviewer).build(),
                    contentDescription = review.author, modifier = Modifier
                        .clip(CircleShape)
                        .size(44.dp))
                Spacer(modifier = Modifier.height(14.dp))
                Text(text = review.rating?.toString() ?: "",
                    style = MaterialTheme.typography.h5.copy(color = BlueLight, fontSize = 12.sp))
            }
            Spacer(modifier = Modifier.width(11.dp))
            Column {
                Text(text = review.author,
                    style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Medium,
                        fontSize = 12.sp, color = Color.White))
                    Text(text = review.content,
                        style = MaterialTheme.typography.h5.copy(fontSize = 12.sp,
                            color = Color.White),
                        maxLines = 7,
                        overflow = TextOverflow.Ellipsis)

            }
        }
    }

}
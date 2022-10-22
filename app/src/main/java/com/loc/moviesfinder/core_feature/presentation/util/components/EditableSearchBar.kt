package com.loc.moviesfinder.core_feature.presentation.util.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.loc.moviesfinder.ui.theme.Gray600
import com.loc.moviesfinder.R

@Composable
fun EditableSearchbar(
    modifier: Modifier = Modifier,
    editable: Boolean,
    onValueChange: (query: String) -> Unit,
) {

    var searchQuery by remember {
        mutableStateOf("")
    }


    BasicTextField(
        value = searchQuery,
        onValueChange = {
            searchQuery = it
            onValueChange(it)
        },
        readOnly = !editable,
        maxLines = 1,
        singleLine = true,
        cursorBrush = SolidColor(Color.White),

    ) {

        Box(modifier = modifier
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colors.surface)) {

            Row(modifier = Modifier
                .fillMaxSize()
                .padding(start = (24.7).dp, end = (17.78).dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {

                if (searchQuery.isEmpty())
                    Text(
                        text = stringResource(id = R.string.search),
                        style = MaterialTheme.typography.h5,
                        color = Gray600,
                    )
                else {
                    Text(
                        text = searchQuery,
                        style = MaterialTheme.typography.h5,
                        color = Color.White,
                    )
                }

                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "search",
                    modifier = Modifier.size(16.dp),
                    tint = Gray600)

            }
        }
    }

}


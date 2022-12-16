package com.loc.moviesfinder.core_feature.presentation.util.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.android.TextLayout
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.loc.moviesfinder.ui.theme.Gray600
import com.loc.moviesfinder.R

@Composable
fun EditableSearchbar(
    modifier: Modifier = Modifier,
    onValueChange: ((query: String) -> Unit)? = null,
) {

    var searchQuery by rememberSaveable {
        mutableStateOf("")
    }

    val showHintState = derivedStateOf {
        searchQuery.isEmpty()
    }

    BasicTextField(
        value = searchQuery,
        onValueChange = {
            searchQuery = it
            if (onValueChange != null)
                onValueChange(it)
        },
        modifier = modifier,
        textStyle = MaterialTheme.typography.h5.copy(color = Color.White, fontSize = 14.sp),
        readOnly = false,
        cursorBrush = Brush.verticalGradient(
            0.00f to Color.Transparent,
            0.1f to Color.Transparent,
            0.1f to Color.White,
            0.90f to Color.White,
            0.90f to Color.Transparent,
            1.00f to Color.Transparent
        ),
    ) { innerTextField ->
        Box(modifier = Modifier
            .fillMaxSize()
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colors.surface)
        ) {

            Row(modifier = Modifier
                .fillMaxSize()
                .padding(start = (24.7).dp, end = (17.78).dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {


                Text(
                    text = stringResource(id = R.string.search),
                    style = MaterialTheme.typography.h5.copy(color = if (showHintState.value) Gray600 else Color.Transparent,
                        fontSize = 14.sp)
                )

                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "search",
                    modifier = Modifier.size(16.dp),
                    tint = Gray600
                )
            }
        }
        Row(modifier = Modifier
            .fillMaxSize()
            .padding(start = (24.7).dp, end = (17.78).dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start) {
            innerTextField()
        }
    }
}


@Composable
fun UnEditableSearchbar(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
) {

    var searchQuery by remember {
        mutableStateOf("")
    }

    val showHintState = derivedStateOf {
        searchQuery.isEmpty()
    }

    BasicTextField(
        value = searchQuery,
        onValueChange = {},
        modifier = modifier,
        textStyle = MaterialTheme.typography.h5.copy(color = Color.White, fontSize = 14.sp),
        readOnly = true,
        cursorBrush = Brush.verticalGradient(
            0.00f to Color.Transparent,
            0.1f to Color.Transparent,
            0.1f to Color.White,
            0.90f to Color.White,
            0.90f to Color.Transparent,
            1.00f to Color.Transparent
        ),
    ) { innerTextField ->
        Box(modifier = Modifier
            .fillMaxSize()
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colors.surface)
            .clickable { if (onClick != null) onClick() }
        ) {

            Row(modifier = Modifier
                .fillMaxSize()
                .padding(start = (24.7).dp, end = (17.78).dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {


                Text(
                    text = stringResource(id = R.string.search),
                    style = MaterialTheme.typography.h5.copy(color = if (showHintState.value) Gray600 else Color.Transparent,
                        fontSize = 14.sp)
                )

                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "search",
                    modifier = Modifier.size(16.dp),
                    tint = Gray600
                )
            }
        }
        Row(modifier = Modifier
            .fillMaxSize()
            .padding(start = (24.7).dp, end = (17.78).dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start) {
            innerTextField()
        }
    }
}



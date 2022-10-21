package com.loc.moviesfinder.core_feature.presentation.util.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.gson.annotations.Until


fun <T> LazyListScope.gridItems(
    data: List<T>,
    columnCount: Int,
    modifier: Modifier,
    verticalSpace: Dp = 18.dp,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalAlignment: Alignment.Vertical = Alignment.Top,
    lastItemReached:()-> Unit,
    itemContent: @Composable BoxScope.(T) -> Unit,
    ) {
    val size = data.count()
    val rows = if (size == 0) 0 else 1 + (size - 1) / columnCount
    items(rows, key = { it.hashCode() }) { rowIndex ->
            Row(
                horizontalArrangement = horizontalArrangement,
                verticalAlignment = verticalAlignment,
                modifier = modifier
            ) {
                for (columnIndex in 0 until columnCount) {
                    val itemIndex = rowIndex * columnCount + columnIndex

                    if (itemIndex == size - 3)
                        lastItemReached()

                    if (itemIndex < size) {
                        Box(
                            modifier = Modifier.weight(1f),
                            propagateMinConstraints = true
                        ) {
                            itemContent(data[itemIndex])
                        }
                    } else {
                    Spacer(Modifier.weight(1f))
                    }
                }
            }
        Spacer(modifier = Modifier.height(verticalSpace))
    }
}
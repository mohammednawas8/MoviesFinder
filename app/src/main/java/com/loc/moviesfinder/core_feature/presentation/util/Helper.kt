package com.loc.moviesfinder.core_feature.presentation.util

fun List<String>.toSingleLine(): String {
    var str = ""
    this.forEachIndexed { i, item ->
        if (i == this.size - 1)
            str += item
        else str += "$item, "

    }
    return str
}
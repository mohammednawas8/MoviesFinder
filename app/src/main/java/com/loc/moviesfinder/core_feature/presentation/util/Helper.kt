package com.loc.moviesfinder.core_feature.presentation.util

import com.loc.moviesfinder.core_feature.data.remote.dao.Genre

fun List<String>.toSingleLine(): String {
    var str = ""
    this.forEachIndexed { i, item ->
        if (i == this.size - 1)
            str += item
        else str += "$item, "

    }
    return str
}

fun String.toStringListOfGenres(): List<String> {
    val genres = this.split(",")
    return genres.map { it.trim() }
}
package com.loc.moviesfinder.core_feature.domain.repository

import android.graphics.Bitmap
import com.loc.moviesfinder.core_feature.domain.util.Resource

interface ImageRepository {

    suspend fun downloadImage(imagePath: String): Resource<Bitmap>
}
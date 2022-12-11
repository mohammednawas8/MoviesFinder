package com.loc.moviesfinder.core_feature.data.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.loc.moviesfinder.core_feature.data.remote.ImageApi
import com.loc.moviesfinder.core_feature.domain.repository.ImageRepository
import com.loc.moviesfinder.core_feature.domain.util.Resource


class ImageRepositoryImpl(
    private val imageApi: ImageApi,
) : ImageRepository {

    override suspend fun downloadImage(imagePath: String): Resource<Bitmap> {
        return try {
            val response = imageApi.downloadImage(imagePath)
            if (response.body() != null) {
                val byteArray = response.body()!!.bytes()
                val bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                Resource.Success(bmp)
            } else {
                throw Exception(getResponseError(response.code()))
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    private fun getResponseError(responseCode: Int): String {
        val strResponseCode = responseCode.toString()
        return when (strResponseCode.drop(0)) {
            "4" -> "Please check your internet"
            "5" -> "Server error"
            else -> "Unknown error"
        }
    }
}
package com.loc.moviesfinder.core_feature.data.worker

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.loc.moviesfinder.core_feature.domain.repository.ImageRepository
import com.loc.moviesfinder.core_feature.domain.util.Resource
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream
import java.io.File
import javax.inject.Inject

@HiltWorker
class DownloadImageWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted private val workerParameters: WorkerParameters,
    private val imageRepository: ImageRepository,
) : CoroutineWorker(appContext, workerParameters) {

    override suspend fun doWork(): Result {
        setProgress(workDataOf("loading" to true))
        val imagePath: String? = workerParameters.inputData.getString("imagePath")
        try {
            if (imagePath != null) {
                val result = imageRepository.downloadImage(imagePath)
                when (result) {
                    is Resource.Success -> {
                        val bmp = result.data
                        saveImage(bmp!!, imagePath)
                        setProgress(workDataOf("loading" to false))
                        return Result.success()
                    }
                    is Resource.Error -> {
                        throw Exception(result.exception)
                    }
                    else -> throw Exception("Unknown result")
                }
            } else {
                throw Exception("imagePath can't be null")
            }
        } catch (e: Exception) {
            setProgress(workDataOf("loading" to false))
            e.printStackTrace()
            return Result.failure(workDataOf("error" to e.message))
        }
    }

    private fun saveImage(bmp: Bitmap, imagePath: String) {

        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.WIDTH, bmp.width)
            put(MediaStore.Images.Media.HEIGHT, bmp.height)
            put(MediaStore.Images.Media.DISPLAY_NAME, imagePath)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        }
        val imagesUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        } else {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }
        val imageUri = appContext.contentResolver.insert(imagesUri, contentValues)
        imageUri?.let {
            appContext.contentResolver.openOutputStream(it).use { stream ->
                if (!bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream))
                    throw Exception("Couldn't save the image")
            }
        }
    }

}

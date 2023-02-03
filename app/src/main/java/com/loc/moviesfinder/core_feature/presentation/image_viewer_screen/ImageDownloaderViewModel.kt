package com.loc.moviesfinder.core_feature.presentation.image_viewer_screen

import android.app.Application
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.loc.moviesfinder.core_feature.data.worker.DownloadImageWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

const val TAG = "ImageDownloader"

@HiltViewModel
class ImageDownloaderViewModel @Inject constructor(
    application: Application,
) : AndroidViewModel(application) {

    private val _downloadState = MutableStateFlow(ImageViewerState())
    val downloadState = _downloadState.asStateFlow()

    private val _message = MutableSharedFlow<String>()
    val message = _message.asSharedFlow()

    fun downloadImage(imagePath: String) {
        val downloadRequest =
            OneTimeWorkRequestBuilder<DownloadImageWorker>().setInputData(workDataOf("imagePath" to imagePath))
                .build()
        WorkManager.getInstance(getApplication())
            .beginUniqueWork("imageSaved", ExistingWorkPolicy.APPEND_OR_REPLACE, downloadRequest).enqueue()
        val workerUUID = downloadRequest.id
        observeChanges(workerUUID)
    }

    private fun observeChanges(workerUUID: UUID) {
        viewModelScope.launch {
            WorkManager.getInstance(getApplication()).getWorkInfoByIdLiveData(workerUUID).asFlow()
                .collectLatest {
                    when (it.state.name) {
                        WorkInfo.State.SUCCEEDED.name -> {
                            _downloadState.value =
                                downloadState.value.copy(loading = false,
                                    success = true)
                            viewModelScope.launch {
                                _message.emit("Image saved")
                            }
                        }
                        WorkInfo.State.RUNNING.name -> {
                            _downloadState.value =
                                downloadState.value.copy(loading = true)

                        }
                        WorkInfo.State.FAILED.name -> {
                            _downloadState.value = downloadState.value.copy(loading = true)
                            viewModelScope.launch {
                                _message.emit("Couldn't save the image")
                            }
                        }
                        else -> Unit
                    }
                }
        }
    }
}
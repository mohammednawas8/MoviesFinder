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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
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


    fun downloadImage(imagePath: String) {
        val downloadRequest =
            OneTimeWorkRequestBuilder<DownloadImageWorker>().setInputData(workDataOf("imagePath" to imagePath))
                .build()
        WorkManager.getInstance(getApplication())
            .beginUniqueWork("imageSaved", ExistingWorkPolicy.KEEP, downloadRequest).enqueue()
        val workerUUID = downloadRequest.id
        observeChanges(workerUUID)
    }

    fun observeChanges(workerUUID: UUID) {
        viewModelScope.launch {
            WorkManager.getInstance(getApplication()).getWorkInfoByIdLiveData(workerUUID).asFlow()
                .collectLatest {
                    when (it.state.name) {
                        WorkInfo.State.SUCCEEDED.name -> {
                            _downloadState.value =
                                downloadState.value.copy(loading = false,
                                    error = null,
                                    success = true)
                        }
                        WorkInfo.State.RUNNING.name -> {
                            _downloadState.value =
                                downloadState.value.copy(loading = true, error = null)
                        }
                        WorkInfo.State.FAILED.name -> {
                            _downloadState.value = downloadState.value.copy(loading = true,
                                error = it.outputData.getString("error"))
                        }
                        else -> Unit
                    }
                }
        }
    }
}
package com.loc.moviesfinder.core_feature.presentation.navigation

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loc.moviesfinder.core_feature.presentation.image_viewer_screen.ImageType
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NavigationViewModel : ViewModel() {
    private val _bottomNavigationState = mutableStateOf(BottomNavigationState())
    val bottomNavigationState: State<BottomNavigationState> = _bottomNavigationState

    private val _movieNavigation = MutableSharedFlow<Int?>()
    val movieNavigation = _movieNavigation.asSharedFlow()

    private val _bottomNavigation = MutableSharedFlow<String>()
    val bottomNavigation = _bottomNavigation.asSharedFlow()

    private val _imageViewerNavigation = MutableSharedFlow<ImageViewerNavigation>()
    val imageViewerNavigation = _imageViewerNavigation.asSharedFlow()

    fun navigateToBottomNavigationScreen(selectedTab: Navigation) {
        _bottomNavigationState.value = BottomNavigationState(selectedTab = selectedTab)
        viewModelScope.launch {
            _bottomNavigation.emit(selectedTab.root)
        }
    }

    fun navigateToMovie(id: Int) {
        viewModelScope.launch {
            _movieNavigation.emit(id)
        }
        _bottomNavigationState.value =
            bottomNavigationState.value.copy(showBottomNavigation = false)
    }

    fun changeSelectedItem(selectedTab: Navigation) {
        _bottomNavigationState.value = BottomNavigationState(selectedTab = selectedTab)
    }

    fun navigateToImageViewer(imagePath: String?, type: ImageType) {
        val imageExtension = imagePath?.removePrefix("https://image.tmdb.org/t/p/w500//")
        if (imageExtension != imagePath && imageExtension != null) {
            viewModelScope.launch {
                _imageViewerNavigation.emit(ImageViewerNavigation(imageExtension,
                    if (type == ImageType.POSTER) "Poster" else "Backdrop"))
            }
        }
    }
}

data class ImageViewerNavigation(
    val path: String,
    val type: String,
)
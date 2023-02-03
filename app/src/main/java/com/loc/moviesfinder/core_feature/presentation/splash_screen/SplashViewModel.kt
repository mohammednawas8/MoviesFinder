package com.loc.moviesfinder.core_feature.presentation.splash_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loc.moviesfinder.core_feature.presentation.navigation.Navigation
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {

    private val _navigate = MutableSharedFlow<String>()
     val navigate = _navigate.asSharedFlow()

    init {
        splashTimer()
    }

    private fun splashTimer() {
        viewModelScope.launch {
            repeat(3){
                delay(1000)
            }
            _navigate.emit(Navigation.HomeScreen.root)
        }
    }
}
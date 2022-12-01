package com.loc.moviesfinder.core_feature.presentation.details_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.loc.moviesfinder.core_feature.data.mapper.correctImagePath
import com.loc.moviesfinder.core_feature.data.remote.paging.ReviewsPagingSource
import com.loc.moviesfinder.core_feature.domain.repository.MoviesRepository
import com.loc.moviesfinder.core_feature.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
) : ViewModel() {

    private val _movieDetails = MutableStateFlow(DetailsScreenState())
    val movieDetails: StateFlow<DetailsScreenState> = _movieDetails

    var movieId by Delegates.notNull<Int>()

    init {
    }

    fun getMovieDetails(movieId: Int) { //Another way to get the id
        //1- Get the movie from database.
        //2- if the movie from the database is null then get it from the api.
        this.movieId = movieId
        viewModelScope.launch {
            val movieDetailsFromDatabase = moviesRepository.getMovieDetailsFromDatabase(movieId)
            if (movieDetailsFromDatabase != null) {
                _movieDetails.value =
                    movieDetails.value.copy(movieDetails = movieDetailsFromDatabase.correctImagePath())
            } else {
                _movieDetails.value = movieDetails.value.copy(isLoading = true)
                val movieDetailsFromNetwork = moviesRepository.getMovieDetailsFromNetwork(movieId)
                when (movieDetailsFromNetwork) {
                    is Resource.Success -> {
                        _movieDetails.value = movieDetails.value.copy(isLoading = false,
                            movieDetails = movieDetailsFromNetwork.data!!.correctImagePath())
                    }
                    is Resource.Error -> {
                        _movieDetails.value = movieDetails.value.copy(isLoading = false,
                            error = movieDetailsFromNetwork.exception.toString())
                    }
                    else -> Unit
                }
            }
        }
    }

    val reviewsPaginator = Pager(
        config = PagingConfig(10),
        initialKey = 1
    ) {
        ReviewsPagingSource(movieId, moviesRepository)
    }.flow
}
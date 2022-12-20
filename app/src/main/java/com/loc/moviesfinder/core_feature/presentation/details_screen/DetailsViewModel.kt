package com.loc.moviesfinder.core_feature.presentation.details_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.loc.moviesfinder.core_feature.data.mapper.correctImagePath
import com.loc.moviesfinder.core_feature.data.remote.paging.ReviewsPagingSource
import com.loc.moviesfinder.core_feature.domain.repository.MoviesRepository
import com.loc.moviesfinder.core_feature.domain.util.Resource
import com.loc.moviesfinder.core_feature.presentation.util.toSingleLine
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
) : ViewModel() {

    private val _movieDetails = MutableStateFlow(DetailsScreenState())
    val movieDetails: StateFlow<DetailsScreenState> = _movieDetails


    var movieId: Int? = null

    var reviewScrollPosition = 0
    var castScrollPosition = 0

    fun getMovie(movieId: Int) {
        this.movieId = movieId
        getMovieDetails(movieId)
        getMovieCast(movieId)
        checkIfMovieIsSaved(movieId)
    }

    private fun checkIfMovieIsSaved(movieId: Int) {
        viewModelScope.launch {
            val movie = moviesRepository.getMovieFromDatabase(movieId)
            if (movie == null)
                _movieDetails.value = movieDetails.value.copy(isSaved = false)
            else
                _movieDetails.value = movieDetails.value.copy(isSaved = true)

        }
    }

    private fun getMovieDetails(movieId: Int) {
        this.movieId = movieId
        viewModelScope.launch {
            _movieDetails.value = movieDetails.value.copy(detailsLoading = true)
            val movieDetailsFromNetwork = moviesRepository.getMovieDetailsFromNetwork(movieId)
            when (movieDetailsFromNetwork) {
                is Resource.Success -> {
                    val genres = movieDetailsFromNetwork.data?.genre ?: emptyList()
                    val genreString = if (genres.isEmpty())
                        ""
                    else
                        genres[0]

                    _movieDetails.value = movieDetails.value.copy(detailsLoading = false,
                        movieDetails = movieDetailsFromNetwork.data!!.correctImagePath(),
                        genreString = genreString,
                        error = null)
                }
                is Resource.Error -> {
                    _movieDetails.value = movieDetails.value.copy(detailsLoading = false,
                        error = movieDetailsFromNetwork.exception.toString())
                }
                else -> Unit
            }
        }
    }


    private fun getMovieCast(movieId: Int) {
        viewModelScope.launch {
            _movieDetails.value = movieDetails.value.copy(castLoading = true)
            val result = moviesRepository.getMovieCast(movieId)
            when (result) {
                is Resource.Success -> {
                    _movieDetails.value =
                        movieDetails.value.copy(castLoading = false,
                            error = null,
                            castList = result.data!!)
                }
                is Resource.Error -> {
                    _movieDetails.value = movieDetails.value.copy(castLoading = false,
                        error = result.exception.toString())
                }
                else -> Unit
            }
        }
    }

    fun insertDeleteMovie() {
        viewModelScope.launch {
            movieId?.let { movieId ->
                val movieFromDatabase = moviesRepository.getMovieFromDatabase(movieId)
                if (movieFromDatabase == null) { //Insert
                    movieDetails.value.movieDetails?.let { movie ->
                        moviesRepository.inertMovie(movie)
                        _movieDetails.value = movieDetails.value.copy(isSaved = true)
                    }
                } else { //Delete
                    moviesRepository.deleteMovie(movieId)
                    _movieDetails.value = movieDetails.value.copy(isSaved = false)
                }
            }
        }
    }

    val reviewsPaginator = Pager(
        config = PagingConfig(10),
        initialKey = 1
    ) {
        ReviewsPagingSource(movieId, moviesRepository)
    }.flow.cachedIn(viewModelScope)
//CachedIn used to save the scroll position
}
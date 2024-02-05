package com.example.tonightsmovie.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.tonightsmovie.common.ui.base.BaseViewModel
import com.example.tonightsmovie.data.MovieDetails
import com.example.tonightsmovie.data.MovieRepository
import com.example.tonightsmovie.ui.moviedetails.MovieDetailsFragmentArgs
import com.example.tonightsmovie.ui.moviedetails.MovieDetailsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val repository: MovieRepository,
    state: SavedStateHandle
) : BaseViewModel() {

    // The user selected movie which is passed to [MovieDetailsFragment]
    private val _argsMovieDetails =
        MovieDetailsFragmentArgs.fromSavedStateHandle(state).movieDetails

    val movieDetails: StateFlow<MovieDetails> get() = _uiState.stateFlowMap {
        when (it) {
            is MovieDetailsUiState.Loaded -> it.movieDetails
            else -> _argsMovieDetails
        }
    }

    // Backing property to avoid state updates from other classes
    private val _uiState: MutableStateFlow<MovieDetailsUiState> = MutableStateFlow(
        MovieDetailsUiState.IDLE
    )

    // The UI collects from this StateFlow to get its state updates
    val uiState: StateFlow<MovieDetailsUiState> = _uiState.asStateFlow()

    init {
        fetchMovieDetails()
    }

    // Fetch the rest of movie's data and update the UiState respectively.
    fun fetchMovieDetails() {

        viewModelScope.launch {
            /** [fetchMovieDetails] Triggers `MovieService` call in the [MovieRepository]
             *  and returns the response to update the UI accordingly.
             */

            // Update with loading state at the beginning
            _uiState.value = MovieDetailsUiState.Loading

            repository.getTrendingMoviesPaging(movieId = _argsMovieDetails.id)
                .flowOn(Dispatchers.IO)
                .catch { exception ->
                    // On exception catch, update with error state.
                    _uiState.value = MovieDetailsUiState.Error(exception)
                }
                .collect {
                    // On successful collect, update with loaded state.
                    _uiState.value = MovieDetailsUiState.Loaded(it)
                }
        }
    }

}
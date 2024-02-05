package com.example.tonightsmovie.viewmodels

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.tonightsmovie.common.ui.base.BaseViewModel
import com.example.tonightsmovie.data.MovieRepository
import com.example.tonightsmovie.ui.home.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: MovieRepository) : BaseViewModel() {

    // Backing property to avoid state updates from other classes
    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.IDLE)

    // The UI collects from this StateFlow to get its state updates
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        getTrendingMoviesPaging()
    }

    /**
     * Start paging trending movies and update the UiState respectively.
     */
    private fun getTrendingMoviesPaging() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.PagingLoaded(
                repository.getTrendingMoviesPaging()
                    .cachedIn(viewModelScope)
                    .first()
            )
        }
    }

}
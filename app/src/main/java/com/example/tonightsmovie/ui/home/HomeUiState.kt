package com.example.tonightsmovie.ui.home

import androidx.paging.PagingData
import com.example.tonightsmovie.common.ui.base.BaseUiState
import com.example.tonightsmovie.data.MovieDetails

sealed class HomeUiState : BaseUiState() {

    /**
     * [IDLE] is just doing nothing, it will be only set before the HomeViewModel.init().
     */
    data object IDLE : HomeUiState()

    /**
     * [PagingLoaded] only means that [PagingData] is ready to load the first page of data.
     */
    data class PagingLoaded(val trendingMovies: PagingData<MovieDetails>) : HomeUiState()

    /**
     * [PagingError] are errors related to setup-ing the [PagingData] only,
     * not loading pages data errors.
     */
    data class PagingError(val error: Throwable) : HomeUiState()

}
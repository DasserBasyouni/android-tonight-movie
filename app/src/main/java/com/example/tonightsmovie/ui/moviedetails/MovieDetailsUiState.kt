package com.example.tonightsmovie.ui.moviedetails

import android.content.Context
import com.example.tonightsmovie.R
import com.example.tonightsmovie.common.ui.base.BaseUiState
import com.example.tonightsmovie.data.MovieDetails
import com.example.tonightsmovie.utils.ext.capitalizeFirstLetter

sealed class MovieDetailsUiState : BaseUiState() {

    /**
     * [IDLE] is just doing nothing, it will be only set before the MovieDetailsViewModel.init().
     */
    data object IDLE : MovieDetailsUiState()

    /**
     * [Loading]
     */
    data object Loading : MovieDetailsUiState()

    /**
     * [Loaded]
     */
    data class Loaded(val movieDetails: MovieDetails) : MovieDetailsUiState()

    /**
     * [Error]
     */
    data class Error(val error: Throwable) : MovieDetailsUiState()

    val isLoading get() = this is Loading
    val isLoaded get() = this is Loaded
    val hasError get() = this is Error

    fun getErrorMessage(context: Context) = if (this is Error) {
        this.error.localizedMessage?.capitalizeFirstLetter()
            ?: context.getString(R.string.msg_something_went_wrong)
    } else null
}
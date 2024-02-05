package com.example.tonightsmovie.common.ui

import android.content.Context
import androidx.paging.LoadState
import com.example.tonightsmovie.R
import com.example.tonightsmovie.utils.ext.capitalizeFirstLetter

/**
 *
 */
data class AppPagingLoadUiState(
    private val loadState: LoadState
) {

    val isLoading get() = loadState is LoadState.Loading

    val hasError get() = loadState is LoadState.Error

    // We can treat NotLoading as `isLoaded=true` since PagingAdapter is automatically loading
    // (not standing IDLE), and the states are only Loading, Error and NotLoading.
    val isLoaded get() = loadState is LoadState.NotLoading

    fun getErrorMessage(context: Context) = if (loadState is LoadState.Error) {
        loadState.error.localizedMessage?.capitalizeFirstLetter()
            ?: context.getString(R.string.msg_something_went_wrong)
    } else null

}
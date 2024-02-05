package com.example.tonightsmovie.common.ui

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter


/**
 * An adapter that displays a loading spinner when state is LoadState.Loading,
 * and an error message and retry button when state is LoadState.Error.
 */
class AppLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<AppLoadStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ) = AppLoadStateViewHolder(parent, retry)

    override fun onBindViewHolder(
        holder: AppLoadStateViewHolder,
        loadState: LoadState
    ) = holder.bind(loadState)

}
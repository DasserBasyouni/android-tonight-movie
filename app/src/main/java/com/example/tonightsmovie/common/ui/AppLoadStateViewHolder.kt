package com.example.tonightsmovie.common.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.tonightsmovie.R
import com.example.tonightsmovie.databinding.ItemNetworkStateBinding
import com.example.tonightsmovie.utils.ext.capitalizeFirstLetter

class AppLoadStateViewHolder(
    parent: ViewGroup,
    private val retryAction: () -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(R.layout.item_network_state, parent, false)
) {

    private val binding = ItemNetworkStateBinding.bind(itemView)

    fun bind(loadState: LoadState) {

        with(binding) {

            if (loadState is LoadState.Error) {
                tvErrorMessage.text = loadState.error.localizedMessage?.capitalizeFirstLetter()
                    ?: root.context.getString(R.string.msg_something_went_wrong)
                btnRetry.setOnClickListener { retryAction() }
            }

            lpiLoading.visibility = getVisibility(loadState is LoadState.Loading)
            tvErrorMessage.visibility = getVisibility(loadState is LoadState.Error)
            btnRetry.visibility = getVisibility(loadState is LoadState.Error)
        }
    }

    /**
     * Based on @param [isVisible] the function returns [View.INVISIBLE] or [View.GONE].
     *
     * Setting the views to [View.INVISIBLE] instead of [View.GONE],
     * is to make the view height always the same.
     */
    private fun getVisibility(isVisible: Boolean) = if (isVisible) View.VISIBLE else View.INVISIBLE

}
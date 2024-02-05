package com.example.tonightsmovie.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tonightsmovie.R
import com.example.tonightsmovie.data.MovieDetails
import com.example.tonightsmovie.databinding.ItemMovieBinding
import com.example.tonightsmovie.utils.ext.addClickListener

/**
 * [MovieListAdapter] that can display [MovieViewHolder].
 */
class MovieListAdapter(
    private val itemClickListener: (
        item: MovieDetails, transitionView: View
    ) -> Unit
) : PagingDataAdapter<MovieDetails, MovieViewHolder>(MovieDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemBinding = ItemMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        // Set adapter items' click listener.
        val viewHolder = MovieViewHolder(itemBinding)
        viewHolder.addClickListener { position, _ ->
            if (position != RecyclerView.NO_POSITION) {

                // Set poster image's transitionName (for the use of Shared Element transition)
                ViewCompat.setTransitionName(
                    itemBinding.ivPoster,
                    itemBinding.root.context.getString(R.string.transition_movie_poster)
                )

                // Set item click listener
                getItem(position)?.let { itemClickListener.invoke(it, itemBinding.ivPoster) }
            }
        }

        return viewHolder
    }

    class MovieDiffCallBack : DiffUtil.ItemCallback<MovieDetails>() {
        override fun areItemsTheSame(oldItem: MovieDetails, newItem: MovieDetails): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: MovieDetails, newItem: MovieDetails): Boolean =
            oldItem == newItem
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) =
        holder.bind(getItem(position)?.posterURL)

}

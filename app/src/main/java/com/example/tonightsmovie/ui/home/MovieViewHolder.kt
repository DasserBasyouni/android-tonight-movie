package com.example.tonightsmovie.ui.home

import androidx.recyclerview.widget.RecyclerView
import com.example.tonightsmovie.databinding.ItemMovieBinding
import com.example.tonightsmovie.utils.ext.load


class MovieViewHolder(
    private val binding: ItemMovieBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(posterURL: String?) {
        posterURL?.let {
            binding.ivPoster.load(it)
        }
    }
}
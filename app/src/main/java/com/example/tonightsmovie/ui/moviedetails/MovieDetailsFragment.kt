package com.example.tonightsmovie.ui.moviedetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.transition.TransitionInflater
import com.example.tonightsmovie.R
import com.example.tonightsmovie.common.ui.base.BaseFragment
import com.example.tonightsmovie.databinding.FragmentMovieDetailsBinding
import com.example.tonightsmovie.viewmodels.MovieDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint


/**
 * A fragment representing movie details (pre-loaded via args and post-loaded via REST API]).
 */
@AndroidEntryPoint
class MovieDetailsFragment : BaseFragment<FragmentMovieDetailsBinding>(
    R.layout.fragment_movie_details
) {

    override val vm: MovieDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupSharedElementTransition()
    }

    // Shared Element transition is used for the movie poster.
    private fun setupSharedElementTransition() {
        val transition = TransitionInflater.from(requireContext())
            .inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = transition
        sharedElementReturnTransition = transition
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNavigation()
    }

    /**
     * Setup navigation per fragment, since using a separate toolbar for each fragment.
     */
    private fun setupNavigation() {
        val navHostFragment = NavHostFragment.findNavController(this)
        NavigationUI.setupWithNavController(vb.toolbar, navHostFragment)
    }

}
package com.example.tonightsmovie.ui.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tonightsmovie.R
import com.example.tonightsmovie.common.ui.AppLoadStateAdapter
import com.example.tonightsmovie.common.ui.AppPagingLoadUiState
import com.example.tonightsmovie.common.ui.base.BaseFragment
import com.example.tonightsmovie.data.MovieDetails
import com.example.tonightsmovie.databinding.FragmentHomeBinding
import com.example.tonightsmovie.utils.ext.collect
import com.example.tonightsmovie.utils.ext.executeWithAction
import com.example.tonightsmovie.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.map


/**
 * A fragment representing home screen which contains trending movies.
 */
@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    // Setup movies adapter, when needed (by lazy).
    private val moviesAdapter: MovieListAdapter by lazy {
        MovieListAdapter { movieDetails, transitionView ->
            val extras = FragmentNavigatorExtras(
                transitionView to getString(R.string.transition_movie_poster)
            )

            val directions = HomeFragmentDirections
                .actionHomeFragmentToMovieDetailsFragment(movieDetails)
            findNavController().navigate(directions, extras)
        }
    }

    override val vm: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNavigation()
        setupAdapter()
        collectUiState()
    }

    /**
     * Setup navigation per fragment, since using a separate toolbar for each fragment.
     */
    private fun setupNavigation() {
        val navHostFragment = NavHostFragment.findNavController(this)
        NavigationUI.setupWithNavController(vb.toolbar, navHostFragment)
    }

    /**
     * Setup movies' adapter.
     */
    private fun setupAdapter() {
        // Listen to first page loading state, and update XML data-binding respectively.
        collect(
            flow = moviesAdapter.loadStateFlow
                .distinctUntilChangedBy { it.source.refresh }
                .map { it.refresh },
            action = ::setFirstPageLoadUiState
        )

        /**
         * Add [AppLoadStateAdapter] as a footer of movies' adapter,
         * then assign the returned `ConcatAdapter` to movies' Recyclerview.
         */
        vb.rvMovies.adapter = moviesAdapter.withLoadStateFooter(
            footer = AppLoadStateAdapter(moviesAdapter::retry)
        )

        // Adjust the `spanSize` of [ItemNetworkStateBinding], so it takes 2 `spanSize` in the grid.
        GridLayoutManager(requireContext(), 2).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    val isFooterItem = position == moviesAdapter.itemCount
                            && moviesAdapter.itemCount > 0
                    return if (isFooterItem) 2 else 1
                }
            }
            vb.rvMovies.layoutManager = this
        }
    }

    /**
     * Set the status of loading the first movie page, based on @param [loadState]
     * in addition to setting `retryAction` for the [LoadState.Error] case.
     */
    private fun setFirstPageLoadUiState(loadState: LoadState) {
        vb.executeWithAction {
            firstPageLoadUiState = AppPagingLoadUiState(loadState)
            retryAction = moviesAdapter::retry
        }
    }

    private fun collectUiState() {
        collect (
            flow = vm.uiState,
            action = { uiState ->
                // New value received
                when (uiState) {
                    is HomeUiState.IDLE -> {
                        /* Do nothing */
                    }
                    is HomeUiState.PagingLoaded -> submitMoviesData(uiState.trendingMovies)
                    is HomeUiState.PagingError -> setFirstPageLoadUiState(
                        loadState = LoadState.Error(uiState.error)
                    )
                }
            }
        )
    }

    /**
     * Submit movies [PagingData] to the adapter.
     */
    private suspend fun submitMoviesData(trendingMovies: PagingData<MovieDetails>) {
        moviesAdapter.submitData(trendingMovies)
    }

    // TODO no need?
    override fun onResume() {
        super.onResume()

        // Hide ActionBar for current fragment.
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
    }

    // TODO no need?
    override fun onStop() {
        super.onStop()

        // Restore ActionBar for the next fragment/activity.
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
    }

}
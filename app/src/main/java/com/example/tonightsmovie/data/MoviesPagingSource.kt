package com.example.tonightsmovie.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.tonightsmovie.api.MovieService

private const val STARTING_PAGE_INDEX = 1

class MoviesPagingSource(private val service: MovieService) : PagingSource<Int, MovieDetails>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieDetails> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = service.getTrendingMovies(page = page)
            val photos = response.results
            LoadResult.Page(
                data = photos,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (page == response.totalPages) null else page + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieDetails>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            // This loads starting from previous page, but since PagingConfig.initialLoadSize spans
            // multiple pages, the initial load will still load items centered around
            // anchorPosition. This also prevents needing to immediately launch prepend due to
            // prefetchDistance.
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }
}
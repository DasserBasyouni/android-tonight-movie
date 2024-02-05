package com.example.tonightsmovie.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.tonightsmovie.api.MovieService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val service: MovieService,
    private val remoteDataSource: MovieRemoteDataSource
) {

    fun getTrendingMoviesPaging(): Flow<PagingData<MovieDetails>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = { MoviesPagingSource(service) }
        ).flow
    }

    fun getTrendingMoviesPaging(movieId: Int): Flow<MovieDetails> {
        return remoteDataSource.getMovieDetails(movieId = movieId)
    }

    companion object {
        // Note: The Movie Database have a restriction on page size (it can not be changed).
        // [Reference](https://www.themoviedb.org/talk/62793c42d7a70a17a7f5daae#last)
        private const val NETWORK_PAGE_SIZE = 20
    }
}
package com.example.tonightsmovie.data

import com.example.tonightsmovie.api.MovieService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(
    private val service: MovieService
) {

    fun getMovieDetails(movieId: Int): Flow<MovieDetails> {
        return flow {
            emit(
                service.getMovieDetails(movieId = movieId)
            )
        }
    }

}
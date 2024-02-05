package com.example.tonightsmovie.api

import com.example.tonightsmovie.BuildConfig
import com.example.tonightsmovie.data.MovieDetails
import com.example.tonightsmovie.data.TrendingMoviesResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Used to connect to The Movie Database (TMDB) API to fetch movies
 */
interface MovieService {

    /**
     * Get a list of 20 trending movie.
     * [page] is the number of page from which the 20 movies are being fetched,
     * it can be used for pagination.
     */
    @GET("discover/movie")
    suspend fun getTrendingMovies(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("page") page: Int = 1
    ): TrendingMoviesResponse

    /**
     * Get movie details
     */
    @GET("movie/{movieId}")
    suspend fun getMovieDetails(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY
    ): MovieDetails

    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/"

        /**
         * Create an instance of TheMovieDbService interface, with basic logging level.
         */
        fun create(): MovieService {
            val logger = HttpLoggingInterceptor().apply { level = Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MovieService::class.java)
        }
    }

}
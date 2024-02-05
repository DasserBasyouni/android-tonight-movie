package com.example.tonightsmovie.data

import com.google.gson.annotations.SerializedName

data class TrendingMoviesResponse(
    @field:SerializedName("results") val results: List<MovieDetails>,
    @field:SerializedName("total_pages") val totalPages: Int
)
package com.example.tonightsmovie.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetails(
    @field:SerializedName("id") val id: Int,
    @field:SerializedName("title") val title: String,
    @field:SerializedName("overview") val overview: String,
    @field:SerializedName("poster_path") val posterPath: String,
    @field:SerializedName("runtime") val runtime: Int?,
    @field:SerializedName("revenue") val revenue: Long?,
    @field:SerializedName("budget") val budget: Long?
): Parcelable {
    val posterURL get() = "https://image.tmdb.org/t/p/original$posterPath"
}


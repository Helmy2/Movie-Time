package com.example.movie_time.data.movie


import com.google.gson.annotations.SerializedName

data class Poster(
    @SerializedName("file_path")
    val filePath: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
)
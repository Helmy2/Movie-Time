package com.example.movie_time.data

import com.google.gson.annotations.SerializedName

data class Result(
    var type: Int,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    val id: Int,
    @SerializedName("original_title")
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("first_air_date")
    val firstAirDate: String,
    @SerializedName("media_type")
    val mediaType: String,

    val name: String,
    val title: String,

    @SerializedName("profile_path")
    val profilePath: String,

)

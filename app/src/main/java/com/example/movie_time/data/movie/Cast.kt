package com.example.movie_time.data.movie


import com.google.gson.annotations.SerializedName

data class Cast(
    @SerializedName("cast_id")
    val castId: Int,
    @SerializedName("credit_id")
    val creditId: String,
    val id: Int,
    @SerializedName("known_for_department")
    val knownForDepartment: String,
    val name: String,
    val order: Int,
    @SerializedName("profile_path")
    val profilePath: String
)


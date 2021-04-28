package com.example.movie_time.data


data class MovieResponse(
    var category: String = "",
    val results: MutableList<Result>,
    val page: Int,
)
package com.example.movie_time.data


data class MovieResponse(
    val results: MutableList<Result>,
    val page: Int,
)
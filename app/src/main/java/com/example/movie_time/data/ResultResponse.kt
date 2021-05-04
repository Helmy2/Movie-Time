package com.example.movie_time.data


data class ResultResponse(
    val results: MutableList<Result>,
    val page: Int,
)
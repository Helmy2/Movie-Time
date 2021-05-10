package com.example.movie_time.data

import com.google.gson.annotations.SerializedName


data class ResultResponse(
    val results: MutableList<Result>,
    val page: Int,
    @SerializedName("total_pages")
    val totalPages:Int
)
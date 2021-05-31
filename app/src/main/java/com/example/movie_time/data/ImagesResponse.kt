package com.example.movie_time.data

import com.example.movie_time.data.movie.Poster


data class ImagesResponse(
    val backdrops: List<Poster>,
    val posters: List<Poster>,
    val profiles: List<Poster>
)
package com.example.movie_time.api


import com.example.movie_time.data.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path


interface MovieApi {

    // https://api.themoviedb.org/3/movie/popular?api_key=6e63c2317fbe963d76c3bdc2b785f6d1&page=1
    // https://api.themoviedb.org/3/movie/299534?api_key=6e63c2317fbe963d76c3bdc2b785f6d1
    // https://api.themoviedb.org/3/

    companion object {
        const val API_KEY = "?api_key=baf62556ad57430e7e61c1ace8490114"
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val IMAGE_URL = "https://image.tmdb.org/t/p/w500/"
        const val POPULAR_TV = "tv/popular"
        const val TRENDING = "trending/all/day"

    }

    @GET("$TRENDING$API_KEY")
    suspend fun getPopularTV(): MovieResponse
}
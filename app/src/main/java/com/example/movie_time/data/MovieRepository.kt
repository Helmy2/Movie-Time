package com.example.movie_time.data


import android.util.Log
import com.example.movie_time.api.MovieApi
import com.example.movie_time.util.Resource
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val api: MovieApi,
) {
    suspend fun getRestaurants() =
        try {
            val data = api.getPopularTV()
            data.category = "Popular"
            Resource.Success(data)
        } catch (e: Exception){
            Resource.Error(e)
        }
}
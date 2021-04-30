package com.example.movie_time.data


import com.example.movie_time.api.MovieApi
import com.example.movie_time.api.MovieApi.Companion.MOVIE
import com.example.movie_time.util.Resource
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val api: MovieApi,
) {

    suspend fun getTopRatedMovies(page: Int = 1) =
        try {
            val data = api.getTopRatedMovies(page)
            Resource.Success(data)
        } catch (e: Exception) {
            Resource.Error(e)
        }

    suspend fun getTrending() =
        try {
            val data = api.getTrending()
            Resource.Success(data)
        } catch (e: Exception) {
            Resource.Error(e)
        }

    suspend fun getDetails(id: Int) =
        try {
            val data = api.getMovieDetails(id)
            Resource.Success(data)
        } catch (e: Exception) {
            Resource.Error(e)
        }

    suspend fun getPopular(type: Int = MOVIE) =
        if (type == 1) {
            try {
                val data = api.getPopularMovies()
                Resource.Success(data)
            } catch (e: Exception) {
                Resource.Error(e)
            }
        } else {
            try {
                val data = api.getPopularTV()
                Resource.Success(data)
            } catch (e: Exception) {
                Resource.Error(e)
            }
        }

}
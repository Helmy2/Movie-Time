package com.example.movie_time.data


import com.example.movie_time.api.MovieApi
import com.example.movie_time.util.Resource
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val api: MovieApi,
) {

    suspend fun getTopRatedMovies(page: Int = 1) =
        try {
            val data = api.getTopRatedMovies(page)
            data.category = "Top Rated Movies"

            Resource.Success(data)
        } catch (e: Exception) {
            Resource.Error(e)
        }

    suspend fun getTrending() =
        try {
            val data = api.getTrending()
            data.category = "Trending"

            Resource.Success(data)
        } catch (e: Exception) {
            Resource.Error(e)
        }

    suspend fun getPopularMovies() =
        try {
            val data = api.getPopularMovies()
            data.category = "Popular Movies"

            Resource.Success(data)
        } catch (e: Exception) {
            Resource.Error(e)
        }

}
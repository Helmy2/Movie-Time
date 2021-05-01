package com.example.movie_time.data


import com.example.movie_time.api.MovieApi
import com.example.movie_time.api.MovieApi.Companion.MOVIE
import com.example.movie_time.api.MovieApi.Companion.TV
import com.example.movie_time.util.Resource
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val api: MovieApi,
) {

//    suspend fun getTopRatedMovies(page: Int = 1) =
//        try {
//            val data = api.getTopRatedMovies(page)
//            Resource.Success(data)
//        } catch (e: Exception) {
//            Resource.Error(e)
//        }

    suspend fun getTrending() =
        try {
            val data = api.getTrending()
            data.results.map {
                if (it.mediaType == "movie")
                    it.type = MOVIE
                else
                    it.type = TV
            }
            Resource.Success(data)
        } catch (e: Exception) {
            Resource.Error(e)
        }


    suspend fun getPopular(type: Int) =
        if (type == MOVIE) {
            try {
                val data = api.getPopularMovies()
                data.results.map {
                    it.type = MOVIE
                }
                Resource.Success(data)
            } catch (e: Exception) {
                Resource.Error(e)
            }
        } else {
            try {
                val data = api.getPopularTV()
                data.results.map {
                    it.type = TV
                }
                Resource.Success(data)
            } catch (e: Exception) {
                Resource.Error(e)
            }
        }


    suspend fun getMovieDetails(id: Int) = try {
        val data = api.getMovieDetails(id)
        Resource.Success(data)
    } catch (e: Exception) {
        Resource.Error(e)
    }

}
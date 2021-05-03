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

    suspend fun getTrending(timeWindow: String) =
        try {
            val data = api.getTrending(timeWindow)
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

    //movie details
    suspend fun getMovieDetails(id: Int) = try {
        val data = api.getMovieDetails(id)
        Resource.Success(data)
    } catch (e: Exception) {
        Resource.Error(e)
    }

    suspend fun getMovieCast(id: Int) = try {
        val data = api.getMovieCast(id)
        Resource.Success(data.cast)
    } catch (e: Exception) {
        Resource.Error(e)
    }

    suspend fun getMovieRecommendations(id: Int) = try {
        val data = api.getMovieRecommendations(id)
        data.results.map { it.type = MOVIE }
        Resource.Success(data)
    } catch (e: Exception) {
        Resource.Error(e)
    }

    //list
    suspend fun getMovieGenre(id: Int) =

        try {
            val data = api.getMovieGenre(id)
            data.results.map { it.type = MOVIE }
            Resource.Success(data)
        } catch (e: Exception) {
            Resource.Error(e)
        }

    suspend fun getTVGenre(id: Int) =
        try {
            val data = api.getTVGenre(id)
            data.results.map { it.type = TV }
            Resource.Success(data)
        } catch (e: Exception) {
            Resource.Error(e)
        }


    //movie details
    suspend fun getTVDetails(id: Int) = try {
        val data = api.getTVDetails(id)
        Resource.Success(data)
    } catch (e: Exception) {
        Resource.Error(e)
    }

    suspend fun getTVCast(id: Int) = try {
        val data = api.getTVCast(id)
        Resource.Success(data.cast)
    } catch (e: Exception) {
        Resource.Error(e)
    }

    suspend fun getTVRecommendations(id: Int) = try {
        val data = api.getTVRecommendations(id)
        data.results.map { it.type = TV }
        Resource.Success(data)
    } catch (e: Exception) {
        Resource.Error(e)
    }

    suspend fun getCredits(id: Int) = try {

        val dataMovie = api.getMovieCredits(id)
        dataMovie.results.map { it.type = MOVIE }
        val list: MutableList<Result> = mutableListOf()
        list.addAll(dataMovie.results)

        val dataTV = api.getTVCredits(id)
        dataTV.cast.map { it.type = TV }
        dataTV.cast.filter { it.voteAverage > 0 }
        list.addAll(dataTV.cast)

        list.sortByDescending { it.voteAverage }


        Resource.Success(list)
    } catch (e: Exception) {
        Resource.Error(e)
    }

}
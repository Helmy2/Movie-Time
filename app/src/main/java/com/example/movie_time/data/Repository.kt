package com.example.movie_time.data


import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.movie_time.api.MovieApi
import com.example.movie_time.api.MovieApi.Companion.MOVIE
import com.example.movie_time.api.MovieApi.Companion.PERSON
import com.example.movie_time.api.MovieApi.Companion.TV
import com.example.movie_time.ui.list.listPagingSource
import com.example.movie_time.util.Resource
import javax.inject.Inject

class Repository @Inject constructor(
    private val api: MovieApi,
) {

    suspend fun getTrending(timeWindow: String) = try {
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


    suspend fun getMoviesPopular() = try {
        val data = api.getMoviesPopular()
        data.results.map {
            it.type = MOVIE
        }
        Resource.Success(data)
    } catch (e: Exception) {
        Resource.Error(e)
    }

    suspend fun getTVPopular() = try {
        val data = api.getTVPopular()
        data.results.map {
            it.type = TV
        }
        Resource.Success(data)
    } catch (e: Exception) {
        Resource.Error(e)
    }


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


    fun getListResults(id: Int, type: Int) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { listPagingSource(api, id, type) }
        ).liveData

    suspend fun getSearch(query: String) = try {
        val data = api.getSearch(query)
        data.results.map {
            when (it.mediaType) {
                "movie" -> it.type = MOVIE
                "tv" -> it.type = TV
                "person" -> it.type = PERSON
            }
        }
        Resource.Success(data)
    } catch (e: Exception) {
        Resource.Error(e)
    }


    suspend fun getMovieImages(id: Int) = try {
        val data = api.getMovieImages(id)
        Resource.Success(data)
    } catch (e: Exception) {
        Resource.Error(e)
    }

    suspend fun getTVImages(id: Int) = try {
        val data = api.getTVImages(id)
        Resource.Success(data)
    } catch (e: Exception) {
        Resource.Error(e)
    }

    suspend fun getPersonDetails(id: Int) = try {
        val data = api.getPersonDetails(id)
        Resource.Success(data)
    } catch (e: Exception) {
        Resource.Error(e)
    }

    suspend fun getPersonImages(id: Int) = try {
        val data = api.getPersonImages(id)
        Resource.Success(data)
    } catch (e: Exception) {
        Resource.Error(e)
    }

    suspend fun getTVCredits(id: Int) = try {
        val data = api.getTVCredits(id)
        data.cast.map { it.type = TV }
        Resource.Success(data)
    } catch (e: Exception) {
        Resource.Error(e)
    }

    suspend fun getMovieCredits(id: Int) = try {
        val data = api.getMovieCredits(id)
        data.cast.map { it.type = MOVIE }
        Resource.Success(data)
    } catch (e: Exception) {
        Resource.Error(e)
    }
}
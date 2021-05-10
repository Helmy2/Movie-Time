package com.example.movie_time.ui.list

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movie_time.api.MovieApi
import com.example.movie_time.api.MovieApi.Companion.CREDITS
import com.example.movie_time.api.MovieApi.Companion.MOVIE
import com.example.movie_time.api.MovieApi.Companion.TV
import com.example.movie_time.data.Result
import retrofit2.HttpException
import java.io.IOException


class listPagingSource(
    private val movieApi: MovieApi,
    private val id: Int,
    private val type: Int,
) : PagingSource<Int, Result>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        val position = params.key ?: 1

        return try {
            var list: MutableList<Result> = mutableListOf()
            when (type) {
                MOVIE -> {
                    val response = movieApi.getMovieGenre(id, position)
                    list = response.results
                    list.map { it.type = MOVIE }
                }
                TV -> {
                    val response = movieApi.getTVGenre(id, position)
                    list = response.results
                    list.map { it.type = TV }
                }
                CREDITS -> {
                    if (position == 1) {
                        val responseMovie = movieApi.getMovieCredits(id)
                        responseMovie.cast.map { it.type = MOVIE }
                        list.addAll(responseMovie.cast.filter { it.voteAverage > 0 })

                        val responseTV = movieApi.getTVCredits(id)
                        responseTV.cast.map { it.type = TV }
                        list.addAll(responseTV.cast.filter { it.voteAverage > 0 })

                        list.sortByDescending { it.voteAverage }
                    }
                }
            }

            LoadResult.Page(
                data = list,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (list.isEmpty()) null else position + 1
            )

        } catch (exception: IOException) {
            Log.i("TAG", "load: ${exception.message}")
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            Log.i("TAG", "load: ${exception.message}")
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {

        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }

    }
}

//suspend fun getCredits(id: Int) = try {
//    val dataMovie = api.getMovieCredits(id)
//    dataMovie.results.map { it.type = MOVIE }
//
//    val dataTV = api.getTVCredits(id)
//    dataTV.cast.map { it.type = TV }
//
//    val list: MutableList<Result> = mutableListOf()
//    list.addAll(dataMovie.results)
//    list.addAll(dataTV.cast.filter { it.voteAverage > 0 })
//    list.sortByDescending { it.voteAverage }
//
//    Resource.Success(list)
//} catch (e: Exception) {
//    Resource.Error(e)
//}
package com.example.movie_time.api


import com.example.movie_time.data.MovieResponse
import com.example.movie_time.data.Result
import com.example.movie_time.data.TV
import com.example.movie_time.data.movie.Movie
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MovieApi {
    // https://api.themoviedb.org/3/movie/popular?api_key=6e63c2317fbe963d76c3bdc2b785f6d1&page=1
    // https://api.themoviedb.org/3/movie/299534?api_key=6e63c2317fbe963d76c3bdc2b785f6d1
    // https://api.themoviedb.org/3/
    // https://api.themoviedb.org/3/movie/top_rated?api_key=baf62556ad57430e7e61c1ace8490114
    companion object {
        const val API_KEY = "?api_key=baf62556ad57430e7e61c1ace8490114"
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val IMAGE_URL = "https://image.tmdb.org/t/p/w500/"
        const val MOVIE = 1
        const val TV = 2
    }

    @GET("movie/top_rated$API_KEY")
    suspend fun getTopRatedMovies(@Query("page") page: Int = 1): MovieResponse

    @GET("trending/all/day$API_KEY")
    suspend fun getTrending(): MovieResponse

    @GET("movie/popular$API_KEY")
    suspend fun getPopularMovies(): MovieResponse

    @GET("tv/popular$API_KEY")
    suspend fun getPopularTV(): MovieResponse

    @GET("movie/{id}$API_KEY")
    suspend fun getMovieDetails(@Path("id") id:Int): Movie

    @GET("tv/{id}$API_KEY")
    suspend fun getTVDetails(@Path("id") id:Int): TV

}
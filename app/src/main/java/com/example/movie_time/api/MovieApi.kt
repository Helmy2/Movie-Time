package com.example.movie_time.api


import com.example.movie_time.data.CreditResponse
import com.example.movie_time.data.ResultResponse
import com.example.movie_time.data.tv.TV
import com.example.movie_time.data.movie.CastResponse
import com.example.movie_time.data.movie.Movie
import com.example.movie_time.data.ImagesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MovieApi {

    companion object {
        const val API_KEY = "?api_key=baf62556ad57430e7e61c1ace8490114"
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val IMAGE_URL = "https://image.tmdb.org/t/p/w500/"
        const val IMAGE_URL_ORIGINAL = "https://image.tmdb.org/t/p/original/"
        const val MOVIE = 1
        const val TV = 2
        const val PERSON = 3
        const val DAY = "day"
        const val WEEK = "week"
    }


    @GET("movie/top_rated$API_KEY")
    suspend fun getTopRatedMovies(@Query("page") page: Int = 1): ResultResponse

    @GET("discover/movie$API_KEY&sort_by=popularity.desc")
    suspend fun getMovieGenre(
        @Query("with_genres") id: Int,
        @Query("page") page: Int
    ): ResultResponse

    @GET("movie/popular$API_KEY")
    suspend fun getMoviesPopular(): ResultResponse

    @GET("movie/{id}$API_KEY")
    suspend fun getMovieDetails(@Path("id") id: Int): Movie

    @GET("movie/{id}/credits$API_KEY")
    suspend fun getMovieCast(@Path("id") id: Int): CastResponse

    @GET("movie/{id}/recommendations$API_KEY")
    suspend fun getMovieRecommendations(@Path("id") id: Int): ResultResponse

    @GET("tv/{id}$API_KEY")
    suspend fun getTVDetails(@Path("id") id: Int): TV

    @GET("tv/popular$API_KEY")
    suspend fun getTVPopular(): ResultResponse

    @GET("tv/{id}/credits$API_KEY")
    suspend fun getTVCast(@Path("id") id: Int): CastResponse

    @GET("tv/{id}/recommendations$API_KEY")
    suspend fun getTVRecommendations(@Path("id") id: Int): ResultResponse

    @GET("discover/tv$API_KEY&sort_by=popularity.desc")
    suspend fun getTVGenre(@Query("with_genres") id: Int, @Query("page") page: Int): ResultResponse

    @GET("person/{id}/tv_credits$API_KEY")
    suspend fun getTVCredits(@Path("id") id: Int): CreditResponse

    @GET("person/{id}/movie_credits$API_KEY")
    suspend fun getMovieCredits(@Path("id") id: Int): CreditResponse

    @GET("trending/all/{time_window}$API_KEY")
    suspend fun getTrending(@Path("time_window") timeWindow: String): ResultResponse

    @GET("search/multi$API_KEY")
    suspend fun getSearch(@Query("query") query: String): ResultResponse


    @GET("movie/{id}/images$API_KEY")
    suspend fun getMovieImages(@Path("id") id: Int): ImagesResponse

    @GET("tv/{id}/images$API_KEY")
    suspend fun getTVImages(@Path("id") id: Int): ImagesResponse

}
package com.example.movie_time.data.tv


import com.example.movie_time.data.movie.Genre
import com.google.gson.annotations.SerializedName

data class TV(
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("episode_run_time")
    val episodeRunTime: List<Int>,
    @SerializedName("first_air_date")
    val firstAirDate: String,
    val genres: List<Genre>,
    val id: Int,
    @SerializedName("in_production")
    val inProduction: Boolean,
    val languages: List<String>,
    @SerializedName("last_air_date")
    val lastAirDate: String,
    @SerializedName("last_episode_to_air")
    val lastEpisodeToAir: LastEpisodeToAir,
    val name: String,
    @SerializedName("next_episode_to_air")
    val nextEpisodeToAir: Any,
    @SerializedName("number_of_episodes")
    val numberOfEpisodes: Int,
    @SerializedName("number_of_seasons")
    val numberOfSeasons: Int,
    @SerializedName("origin_country")
    val originCountry: List<String>,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String,
    val status: String,
    val tagline: String,
    val type: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int
)

data class LastEpisodeToAir(
    @SerializedName("air_date")
    val airDate: String,
    @SerializedName("episode_number")
    val episodeNumber: Int,
    val id: Int,
    val name: String,
    val overview: String,
    @SerializedName("production_code")
    val productionCode: String,
    @SerializedName("season_number")
    val seasonNumber: Int,
    @SerializedName("still_path")
    val stillPath: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int
)
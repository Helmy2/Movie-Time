package com.example.movie_time.ui.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_time.api.MovieApi.Companion.MOVIE
import com.example.movie_time.api.MovieApi.Companion.TV
import com.example.movie_time.data.Repository
import com.example.movie_time.data.Result
import com.example.movie_time.data.movie.Cast
import com.example.movie_time.data.movie.Movie
import com.example.movie_time.data.movie.Poster
import com.example.movie_time.data.tv.TV
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _movieDetailsData = MutableLiveData<Movie>()
    val movieDetailsData: LiveData<Movie>
        get() = _movieDetailsData

    private val _tvDetailsData = MutableLiveData<TV>()
    val tvDetailsData: LiveData<TV>
        get() = _tvDetailsData

    private val _images = MutableLiveData<List<Poster>>()
    val images: LiveData<List<Poster>>
        get() = _images

    private val _recommendationsData = MutableLiveData<List<Result>>()
    val recommendationsData: LiveData<List<Result>>
        get() = _recommendationsData

    private val _castData = MutableLiveData<List<Cast>>()
    val castData: LiveData<List<Cast>>
        get() = _castData

    fun refresh(id: Int, type: Int) {
        when (type) {
            MOVIE -> {
                getMovieDetails(id)
                getMovieCast(id)
                getMovieRecommendations(id)
                getMovieImages(id)
            }
            TV -> {
                getTVDetails(id)
                getTVCast(id)
                getTVRecommendations(id)
                getTVImages(id)
            }
        }
    }

    private fun getMovieDetails(id: Int) = viewModelScope.launch {
        val response = repository.getMovieDetails(id)
        if (response.error == null) {
            _movieDetailsData.value = response.data!!
        } else {
            Log.i("TAG", response.error.message.toString())
        }
    }

    private fun getMovieCast(id: Int) = viewModelScope.launch {
        val response = repository.getMovieCast(id)
        if (response.error == null)
            _castData.value = response.data!!
        else {
            Log.i("TAG", response.error.message.toString())
        }
    }

    private fun getMovieRecommendations(id: Int) = viewModelScope.launch {
        val response = repository.getMovieRecommendations(id)
        if (response.error == null)
            _recommendationsData.value = response.data?.results
        else {
            Log.i("TAG", response.error.message.toString())
        }
    }

    private fun getMovieImages(id: Int) = viewModelScope.launch {
        val response = repository.getMovieImages(id)
        if (response.error == null) {
            Log.i("TAG", "getMovieImages: ${response.data.toString()}")
            if (response.data != null) {
                _images.value = response.data.backdrops
            }
        } else {
            Log.i("TAG", response.error.message.toString())
        }
    }


    private fun getTVDetails(id: Int) = viewModelScope.launch {
        val response = repository.getTVDetails(id)
        if (response.error == null)
            _tvDetailsData.value = response.data!!
        else {
            Log.i("TAG", response.error.message.toString())
        }
    }

    private fun getTVCast(id: Int) = viewModelScope.launch {
        val response = repository.getTVCast(id)
        if (response.error == null)
            _castData.value = response.data!!
        else {
            Log.i("TAG", response.error.message.toString())
        }
    }

    private fun getTVRecommendations(id: Int) = viewModelScope.launch {
        val response = repository.getTVRecommendations(id)
        if (response.error == null)
            _recommendationsData.value = response.data?.results
        else {
            Log.i("TAG", response.error.message.toString())
        }
    }

    private fun getTVImages(id: Int) = viewModelScope.launch {
        val response = repository.getTVImages(id)
        if (response.error == null) {
            Log.i("TAG", "getTVImages: ${response.data.toString()}")
            if (response.data != null) {
                _images.value = response.data.backdrops
            }
        } else {
            Log.i("TAG", response.error.message.toString())
        }
    }
}
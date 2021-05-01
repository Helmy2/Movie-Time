package com.example.movie_time.ui.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_time.data.MovieRepository
import com.example.movie_time.data.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    movieRepository: MovieRepository
) : ViewModel() {

    private val repository = movieRepository

    private val _detailsData = MutableLiveData<String>()
    val detailsData: LiveData<String>
        get() = _detailsData

    fun getPopular(id: Int) = viewModelScope.launch {
        val movieResponse = repository.getMovieDetails(id)
        if (movieResponse.error == null)
            _detailsData.value = movieResponse.data!!.toString()
        else
            Log.i("TAG", movieResponse.error.message.toString())
    }
}
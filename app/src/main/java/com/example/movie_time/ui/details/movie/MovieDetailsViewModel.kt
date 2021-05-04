package com.example.movie_time.ui.details.movie

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_time.data.Repository
import com.example.movie_time.data.Result
import com.example.movie_time.data.movie.Cast
import com.example.movie_time.data.movie.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    Repository: Repository
) : ViewModel() {

    private val repository = Repository

    private val _detailsData = MutableLiveData<Movie>()
    val detailsData: LiveData<Movie>
        get() = _detailsData

    private val _recommendationsData = MutableLiveData<List<Result>>()
    val recommendationsData: LiveData<List<Result>>
        get() = _recommendationsData

    private val _castData = MutableLiveData<List<Cast>>()
    val castData: LiveData<List<Cast>>
        get() = _castData

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?>
        get() = _error


    fun refresh(id: Int) {
        getMovieDetails(id)
        getMovieCast(id)
        getMovieRecommendations(id)
    }

    private fun getMovieDetails(id: Int) = viewModelScope.launch {
        val response = repository.getMovieDetails(id)
        if (response.error == null)
            _detailsData.value = response.data!!
        else {
            _error.value = response.error.localizedMessage
            Log.i("TAG", response.error.message.toString())
        }
    }

    private fun getMovieCast(id: Int) = viewModelScope.launch {
        val response = repository.getMovieCast(id)
        if (response.error == null)
            _castData.value = response.data!!
        else {
            _error.value = response.error.localizedMessage
            Log.i("TAG", response.error.message.toString())
        }
    }

    private fun getMovieRecommendations(id: Int) = viewModelScope.launch {
        val response = repository.getMovieRecommendations(id)
        if (response.error == null)
            _recommendationsData.value = response.data?.results
        else {
            _error.value = response.error.localizedMessage
            Log.i("TAG", response.error.message.toString())
        }
    }

}
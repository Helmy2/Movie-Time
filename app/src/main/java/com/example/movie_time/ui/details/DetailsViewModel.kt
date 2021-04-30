package com.example.movie_time.ui.details

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
class DetailsViewModel @Inject constructor(
    movieRepository: MovieRepository
) : ViewModel() {

    private val repository = movieRepository

    private val _detailsData = MutableLiveData<Result>()
    val detailsData: LiveData<Result>
        get() = _detailsData

    fun getPopular(id: Int) = viewModelScope.launch {
        val movieResponse = repository.getDetails(id)
        _detailsData.value = movieResponse.data!!
    }
}
package com.example.movie_time.ui.home


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_time.api.MovieApi
import com.example.movie_time.data.Movie
import com.example.movie_time.data.MovieRepository
import com.example.movie_time.data.MovieResponse
import com.example.movie_time.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    movieRepository: MovieRepository
) : ViewModel() {

    private val _restaurants = MutableLiveData<
            Resource<MovieResponse>>()

    val restaurants: LiveData<
           Resource<MovieResponse>> = _restaurants

    init {
        viewModelScope.launch {
            val data = movieRepository.getRestaurants()
            _restaurants.value = data
        }
    }

}
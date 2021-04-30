package com.example.movie_time.ui.tv


import androidx.lifecycle.ViewModel
import com.example.movie_time.data.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TVViewModel @Inject constructor(
    movieRepository: MovieRepository
) : ViewModel() {

}
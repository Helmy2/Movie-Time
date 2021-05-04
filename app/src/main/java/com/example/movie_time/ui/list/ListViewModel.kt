package com.example.movie_time.ui.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_time.api.MovieApi.Companion.CREDITS
import com.example.movie_time.api.MovieApi.Companion.MOVIE
import com.example.movie_time.api.MovieApi.Companion.TV
import com.example.movie_time.data.Repository
import com.example.movie_time.data.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    Repository: Repository
) : ViewModel() {

    private val repository = Repository

    private val _listData = MutableLiveData<List<Result>>()
    val listData: LiveData<List<Result>>
        get() = _listData

     fun refresh(id: Int, type: Int) {
        when(type){
            MOVIE -> getMovieGenre(id)
            CREDITS -> getCredits(id)
            TV -> getTVGenre(id)
        }
    }

    private fun getMovieGenre(id: Int) = viewModelScope.launch {
        val response = repository.getMovieGenre(id)

        if (response.error == null) {
            _listData.value = response.data?.results
            Log.i("TAG", response.data?.results.toString())
        } else {
            Log.i("TAG", response.error.message.toString())
        }
    }

    private fun getTVGenre(id: Int) = viewModelScope.launch {
        val response = repository.getTVGenre(id)
        if (response.error == null) {
            _listData.value = response.data?.results
            Log.i("TAG", response.data?.results.toString())
        } else {
            Log.i("TAG", response.error.message.toString())
        }
    }

    private fun getCredits(id: Int) = viewModelScope.launch {
        val response = repository.getCredits(id)
        if (response.error == null) {
            _listData.value = response.data!!
            Log.i("TAG", response.data.toString())
        } else {
            Log.i("TAG", response.error.message.toString())
        }
    }

}
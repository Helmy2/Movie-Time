package com.example.movie_time.ui.search


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_time.data.Repository
import com.example.movie_time.data.Result
import com.example.movie_time.data.movie.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _searchListData = MutableLiveData<List<Result>>()
    val searchListData: LiveData<List<Result>>
        get() = _searchListData


    fun getSearch(query: String) = viewModelScope.launch {
        val response = repository.getSearch(query)
        if (response.error == null) {
            _searchListData.value = response.data?.results?.filter { it.voteAverage != 0.0 }
            Log.i("TAG", "getSearch: ${response.data?.results.toString()}")
        } else {
            Log.i("TAG", response.error.message.toString())
        }
    }
}
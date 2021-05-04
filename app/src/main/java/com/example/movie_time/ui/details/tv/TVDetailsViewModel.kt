package com.example.movie_time.ui.details.tv

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_time.data.Repository
import com.example.movie_time.data.Result
import com.example.movie_time.data.TV
import com.example.movie_time.data.movie.Cast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TVDetailsViewModel @Inject constructor(
    Repository: Repository
) : ViewModel() {

    private val repository = Repository

    private val _detailsData = MutableLiveData<TV>()
    val detailsData: LiveData<TV>
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
        getTVDetails(id)
        getTVCast(id)
        getTVRecommendations(id)
    }

    private fun getTVDetails(id: Int) = viewModelScope.launch {
        val response = repository.getTVDetails(id)
        if (response.error == null)
            _detailsData.value = response.data!!
        else {
            _error.value = response.error.localizedMessage
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

}
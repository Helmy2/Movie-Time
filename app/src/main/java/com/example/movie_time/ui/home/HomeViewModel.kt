package com.example.movie_time.ui.home


import androidx.lifecycle.*
import com.example.movie_time.api.MovieApi.Companion.DAY
import com.example.movie_time.api.MovieApi.Companion.MOVIE
import com.example.movie_time.data.Repository
import com.example.movie_time.data.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    Repository: Repository
) : ViewModel() {

    private val repository = Repository

    private val _trendingListData = MutableLiveData<List<Result>>()
    val trendingListData: LiveData<List<Result>>
        get() = _trendingListData

    private val _popularListData = MutableLiveData<List<Result>>()
    val popularListData: LiveData<List<Result>>
        get() = _popularListData

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?>
        get() = _error

    private val _counter = MutableLiveData<Int>()
    val counter: LiveData<Int>
        get() = _counter

    init {
        refresh()
        _counter.value = 0
    }

    private var job: Job? = null
    fun pageSwitcher() {
        if (job != null)
            job?.cancel()
        job = viewModelScope.launch {
            delay(5000)
            var num = _counter.value!!
            if (num >= 19)
                num = -1
            _counter.value = num + 1
        }
    }

    fun pagePosition(i: Int) {
        _counter.value = i
    }

    fun refresh() {
        getTrending(DAY)
        getPopular(MOVIE)
    }

    fun getTrending(timeWindow: String) =
        viewModelScope.launch {
            val movieResponse = repository.getTrending(timeWindow)
            _error.value = movieResponse.error?.localizedMessage
            _trendingListData.value = movieResponse.data?.results
        }


    fun getPopular(type: Int) = viewModelScope.launch {
        val movieResponse = if (type == MOVIE)
            repository.getMoviesPopular()
        else
            repository.getTVPopular()

        _error.value = movieResponse.error?.localizedMessage
        _popularListData.value = movieResponse.data?.results
    }

}
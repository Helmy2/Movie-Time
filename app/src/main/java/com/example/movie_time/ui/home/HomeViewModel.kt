package com.example.movie_time.ui.home


import android.util.Log
import androidx.lifecycle.*
import com.example.movie_time.api.MovieApi.Companion.DAY
import com.example.movie_time.api.MovieApi.Companion.MOVIE
import com.example.movie_time.data.MovieRepository
import com.example.movie_time.data.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.android.awaitFrame
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    movieRepository: MovieRepository
) : ViewModel() {

    private val repository = movieRepository

    private val _trendingListData = MutableLiveData<List<Result>>()
    val trendingListData: LiveData<List<Result>>
        get() = _trendingListData

    private val _popularListData = MutableLiveData<List<Result>>()
    val popularListData: LiveData<List<Result>>
        get() = _popularListData

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?>
        get() = _error

    private val _popularType = MutableLiveData<Int>()
    val popularType: LiveData<Int>
        get() = _popularType

    private val _trendingType = MutableLiveData<String>()
    val trendingType: LiveData<String>
        get() = _trendingType

    private val _counter = MutableLiveData<Int>()
    val counter: LiveData<Int>
        get() = _counter

    init {
        if (trendingListData.value == null) {
            refresh()
        }
        _popularType.value = MOVIE
        _trendingType.value = DAY
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
    fun pagePosition(i:Int){
        _counter.value = i
    }

    suspend fun refreshDelay() {
        delay(2000)
        refresh()
    }

    private fun refresh() {
        getTrending(DAY)
        getPopular(MOVIE)
    }
//
//    private fun getTopRatedMovies(page: Int) = viewModelScope.launch {
//        delay(100)
//        val movieResponse = repository.getTopRatedMovies(page)
//        _error.value = movieResponse.error?.localizedMessage
//
//        val list: MutableList<MovieResponse> = mutableListOf()
//        _restaurants.value?.let { list.addAll(it) }
//
//        val exist = list.any { it.category == movieResponse.data?.category }
//        if (exist) {
//            movieResponse.data?.results?.forEach { result ->
//                list.forEach { movieResponse ->
//                    if (result !in movieResponse.results)
//                        movieResponse.results.add(result)
//                }
//            }
//        } else
//            movieResponse.data?.let { list.add(it) }
//
//        _restaurants.value = list
//    }

    fun getTrending(timeWindow: String) =
        viewModelScope.launch {
            val movieResponse = repository.getTrending(timeWindow)
            _error.value = movieResponse.error?.localizedMessage
            _trendingListData.value = movieResponse.data?.results
            _trendingType.value = timeWindow
        }


    fun getPopular(type: Int) = viewModelScope.launch {
        val movieResponse = repository.getPopular(type)
        _error.value = movieResponse.error?.localizedMessage
        _popularListData.value = movieResponse.data?.results
        _popularType.value = type
    }

}
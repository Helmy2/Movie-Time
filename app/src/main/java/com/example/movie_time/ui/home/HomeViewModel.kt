package com.example.movie_time.ui.home


import androidx.lifecycle.*
import com.example.movie_time.data.MovieRepository
import com.example.movie_time.data.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    movieRepository: MovieRepository
) : ViewModel() {

    private val repository = movieRepository

    private val _headListData = MutableLiveData<List<Result>>()
    val headListData: LiveData<List<Result>>
        get() = _headListData


    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?>
        get() = _error


    init {
        if (headListData.value == null) {
            refresh()
        }
    }

    suspend fun refreshDelay(){
        delay(2000)
        refresh()
    }

    private fun refresh(){
        getTrending()
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

    private fun getTrending(){
        viewModelScope.launch {
            val movieResponse = repository.getTrending()
            _error.value = movieResponse.error?.localizedMessage
            _headListData.value = movieResponse.data?.results
        }
    }

//        private fun getPopular() = viewModelScope.launch {
//            delay(100)
//            val movieResponse = repository.getPopularMovies()
//            _error.value = movieResponse.error?.localizedMessage
//
//            val list = mutableListOf<MovieResponse>()
//            _restaurants.value?.let { list.addAll(it) }
//
//            if (movieResponse.data !in list)
//                movieResponse.data?.let { list.add(it) }
//
//            _restaurants.value = list
//        }
//    }

}
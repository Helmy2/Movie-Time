package com.example.movie_time.ui.list

import androidx.lifecycle.*
import com.example.movie_time.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    repository: Repository
) : ViewModel() {

    private val currentId = MutableLiveData(0)
    private val currentType = MutableLiveData(0)

    val listData = currentId.switchMap {
        repository.getListResults(it,currentType.value!!)
    }

    fun setId(id: Int, type: Int) {
        currentId.value = id
        currentType.value = type
    }

}
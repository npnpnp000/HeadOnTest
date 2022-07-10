package com.headontest.viewModel

import androidx.lifecycle.*
import com.adviserall22spdaslld.model.response.Data
import com.headontest.model.Repository
import kotlinx.coroutines.launch


class MainActivityViewModel(private val repository: Repository) : ViewModel(){

    var allDataLiveData : MutableLiveData<List<Data>?> =  MutableLiveData<List<Data>?>()


    fun getRequest() = viewModelScope.launch {
        allDataLiveData.value = repository.getRequest().data
    }
}



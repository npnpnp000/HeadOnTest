package com.headontest.viewModel

import android.net.ConnectivityManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.headontest.model.Repository

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val repository: Repository,private val connectivityManager : ConnectivityManager?): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when(modelClass){
            MainActivityViewModel::class.java ->  MainActivityViewModel(repository) as T
            MainViewModel::class.java ->  MainViewModel(repository) as T
            TreatmentDateViewModel::class.java ->  TreatmentDateViewModel(repository) as T
            MapViewModel::class.java ->  MapViewModel(repository) as T
            SplashViewModel::class.java ->  SplashViewModel(repository, connectivityManager) as T
            else -> throw Exception("Not Fund view model class")
        }



}
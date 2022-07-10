package com.headontest.viewModel

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.*
import com.headontest.model.Repository


class SplashViewModel(private val repository: Repository,private val connectivityManager: ConnectivityManager?) : ViewModel(){


    var errorLiveData : MutableLiveData<String?> = MutableLiveData<String?>(null)



    fun checkInternetConnection(){
        if (!internetConnection()) errorLiveData.value = "No Internet Connection"
    }

    private fun internetConnection(): Boolean {

        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)) {
                    Log.e("Internet", "NetworkCapabilities.TRANSPORT_VPN")
                    return true
                }
            }
        }
        return false
    }
}

package com.headontest.view.activity

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.headontest.application.UserApplication
import com.headontest.databinding.ActivityMainBinding
import com.headontest.viewModel.MainActivityViewModel
import com.headontest.viewModel.ViewModelFactory
import java.util.*

class MainActivity : AppCompatActivity() {

     var binding: ActivityMainBinding? = null

    lateinit var mainActivityViewModel: MainActivityViewModel
    lateinit var viewModelFactory: ViewModelFactory

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val userApplication = application as UserApplication

        viewModelFactory = ViewModelFactory(userApplication.repository,null)
        mainActivityViewModel = ViewModelProvider(this, viewModelFactory).get(MainActivityViewModel::class.java)

        observerTest()
        mainActivityViewModel.getRequest()


    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

    override fun onStart() {
        setAppLanguageToHb()  // Set app to Hebrew
        super.onStart()
    }

    private fun setAppLanguageToHb() {
        val config = resources.configuration
        val lang = "iw" // Hebrew language code
        val locale = Locale(lang)
        Locale.setDefault(locale)
        config.setLocale(locale)

        createConfigurationContext(config)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    private fun observerTest() {
        mainActivityViewModel.allDataLiveData.observe(this, { list ->
            Log.e("allDataLiveData.observe",list.toString())
        })
    }
}
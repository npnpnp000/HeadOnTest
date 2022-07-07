package com.headontest.view.fragment.splash

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.headontest.R
import com.headontest.application.UserApplication
import com.headontest.databinding.FragmentSplashBinding
import com.headontest.viewModel.SplashViewModel
import com.headontest.viewModel.ViewModelFactory
import androidx.appcompat.app.AlertDialog
import com.headontest.viewModel.MainActivityViewModel


class SplashFragment() : Fragment() {

     var binding: FragmentSplashBinding? = null

    lateinit var splashViewModel: SplashViewModel
    lateinit var mainActivityViewModel: MainActivityViewModel
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userApplication = requireActivity().application as UserApplication
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        viewModelFactory = ViewModelFactory(userApplication.repository, connectivityManager)
        splashViewModel = ViewModelProvider(this, viewModelFactory).get(SplashViewModel::class.java)
        mainActivityViewModel = ViewModelProvider(this, viewModelFactory).get(MainActivityViewModel::class.java)

        getData() // make call to the data

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSplashBinding.inflate(inflater, container, false)

        splashViewModel.checkInternetConnection()

        observerInternetConnection()

//        splashViewModel.getRequest()


        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun getData() {
//        mainActivityViewModel.getRequest() //
    }
    private fun observerInternetConnection() {
        splashViewModel.errorLiveData.observe(viewLifecycleOwner, { error ->
            Log.e("errorLiveData.observe", error.toString())

            if (error == null) {
                Handler(Looper.myLooper()!!).postDelayed({
                    val action =
                        SplashFragmentDirections.actionSplashFragmentToMainFragment()   // set action navigate to main fragment
                    findNavController().navigate(action)                                 // navigate using the action
                }, 3000)
            } else {

                startErrorDialog(error)
            }
        })
    }

    private fun startErrorDialog(error: String) {
        val alertDialog: android.app.AlertDialog = android.app.AlertDialog.Builder(context).create()
        alertDialog.setTitle(getString(R.string.error))
        alertDialog.setMessage(error)
        alertDialog.setButton(
            AlertDialog.BUTTON_NEUTRAL, getString(R.string.ok)
        ) { dialog, which ->
                dialog.dismiss()
                activity?.finish()
        }
        alertDialog.show()
    }

}

package com.headontest.view.fragment.treatment_date

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.headontest.application.UserApplication
import com.headontest.databinding.FragmentTreatmentDateBinding
import com.headontest.viewModel.ViewModelFactory
import com.headontest.viewModel.TreatmentDateViewModel


class TreatmentDateFragment : Fragment() {

     var binding: FragmentTreatmentDateBinding? =null


    lateinit var treatmentDateViewModel: TreatmentDateViewModel
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userApplication = requireActivity().application as UserApplication

        viewModelFactory = ViewModelFactory(userApplication.repository,null)
        treatmentDateViewModel = ViewModelProvider(this, viewModelFactory).get(TreatmentDateViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTreatmentDateBinding.inflate(inflater, container, false)

//        mainViewModel.insertMessageToRoom(Message((0..100).random().toString()))

        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
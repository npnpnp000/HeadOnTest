package com.headontest.view.fragment.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.headontest.R
import com.headontest.application.UserApplication
import com.headontest.viewModel.ViewModelFactory
import com.headontest.databinding.FragmentMainBinding
import com.headontest.view.adapters.DataAdapter
import com.headontest.viewModel.MainActivityViewModel
import com.headontest.viewModel.MainViewModel
import java.util.*


class MainFragment : Fragment() {

     var binding: FragmentMainBinding? =null


    lateinit var mainViewModel: MainViewModel
    lateinit var mainActivityViewModel: MainActivityViewModel
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var adapter : DataAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userApplication = requireActivity().application as UserApplication

        viewModelFactory = ViewModelFactory(userApplication.repository,null)
        mainViewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        mainActivityViewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(MainActivityViewModel::class.java)

        adapter = DataAdapter(requireContext()) // installing of list adapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): ConstraintLayout? {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater, container, false)

        setDataObserver()

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setList() // setting of recycler list
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun setDataObserver() {
        mainActivityViewModel.allDataLiveData.observe(viewLifecycleOwner,{ list ->
            list?.let {
                adapter.setData(it)  // when data changed set the list (from the adapter)
            }
        })
    }

    private fun setList() {
        binding?.listItem?.layoutManager = LinearLayoutManager(requireContext()) // default layoutManager
        binding?.listItem?.adapter = adapter  // add the adapter to the list
        adapter.setData(mainActivityViewModel.allDataLiveData.value) // first data set
    }


}
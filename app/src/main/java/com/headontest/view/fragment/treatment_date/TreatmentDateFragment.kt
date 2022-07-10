package com.headontest.view.fragment.treatment_date

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.headontest.R
import com.headontest.application.UserApplication
import com.headontest.databinding.FragmentTreatmentDateBinding
import com.headontest.viewModel.TreatmentDateViewModel
import com.headontest.viewModel.ViewModelFactory
import java.util.*


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


        val locale: Locale = resources.configuration.locale
        Locale.setDefault(locale)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hoursPickerSetting()

        buttonsSetting()

        baseSetting()

    }

    private fun buttonsSetting() {
        //set ok button
        binding?.okTimeDateBtn?.setOnClickListener{
            findNavController().popBackStack() // back to the list
        }
        //set cancel button
        binding?.cancelTimeDateBtn?.setOnClickListener{
            findNavController().popBackStack()
        }
        //set x button in title
        binding?.includeInTimeDate?.topCloseImg?.setOnClickListener{
            findNavController().popBackStack()
        }
        // set time tab (toggle button)
        binding?.timeTgb?.setOnCheckedChangeListener { compoundButton, isChecked ->

            if (isChecked){ // if time tab selected
                binding?.timeAndDateTgb?.isChecked =false // uncheck date tab
                binding?.timeDateMainPanelCsl?.visibility = View.GONE //  make date panel Invisible
            }

        }
        // set date tab (toggle button)
        binding?.timeAndDateTgb?.setOnCheckedChangeListener { compoundButton, isChecked ->

            if (isChecked){ // if time date selected
                binding?.timeTgb?.isChecked =false // uncheck time tab
                binding?.timeDateMainPanelCsl?.visibility = View.VISIBLE // make date panel visible
            }
        }



    }

    private fun baseSetting() {
        // set title text
        binding?.includeInTimeDate?.topFragTxt?.text = getString(R.string.date_of_treatment)

    }

    private fun hoursPickerSetting() {

        binding?.hoursPicker?.minValue = 0  // set the start index
        binding?.hoursPicker?.maxValue = 23 // set the end index
        binding?.hoursPicker?.displayedValues = makeHoursList()  // build the hours list

        val rightNow = Calendar.getInstance(resources.configuration.locale) // get the current time
        val currentHourIn24Format: Int =rightNow.get(Calendar.HOUR_OF_DAY) // return the hour in 24 hrs format (ranging from 0-23)
        binding?.hoursPicker?.value = currentHourIn24Format  // set the numberPicker

    }

    private fun makeHoursList(): Array<String> {

        val hoursList = arrayListOf<String>()

        hoursList.add("00:00")  // add the time 00:00 first
        for (i in 1 until 24){
            hoursList.add("${i}:00")  // add 1:00 til 23:00
        }

        return hoursList.toTypedArray()

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
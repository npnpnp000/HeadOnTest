package com.headontest.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.adviserall22spdaslld.model.response.Data
import com.headontest.R
import com.headontest.databinding.DataCardBinding
import com.headontest.view.fragment.main.MainFragmentDirections
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec


class DataAdapter(val context: Context) : RecyclerView.Adapter<DataAdapter.ViewHolder>()  {

    private var list = listOf<Data>()
    fun setData(newItems: List<Data>?){
        newItems?.let {
            list = it
        }
        notifyDataSetChanged()
    }
    inner class ViewHolder(val binding: DataCardBinding, itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(data: Data) {

            val address = "${data.City}, ${data.Street} ${data.House_number}"
            binding.addressTxt.text = address
            binding.addressTxt.setOnClickListener {
                setBalloonToolTip(it, address) // set Tool Tip
            }

            binding.serialTxt.text = data.ID.toString()

            binding.showOnMapCol.setOnClickListener{
                val action =
                    MainFragmentDirections.actionMainFragmentToMapFragment(data.Latitude.toFloat(),data.Longitude.toFloat())   // set action navigate to main fragment
                binding.root.findNavController().navigate(action)
            }

            binding.treatmentDateCol.setOnClickListener {
                val action =
                    MainFragmentDirections.actionMainFragmentToTreatmentDateFragment(data.ID)  // set action navigate to main fragment
                binding.root.findNavController().navigate(action)
            }
//            val root = binding.root

        }
    }

    private fun setBalloonToolTip(it: View, address: String) {
        val balloon = Balloon.Builder(context)
            .setHeight(BalloonSizeSpec.WRAP)
            .setWidth(BalloonSizeSpec.WRAP)
            .setText(address)
            .setTextColorResource(R.color.black)
            .setTextSize(15f)
//                    .setIconDrawableResource(R.drawable.arrow)
            .setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
            .setArrowSize(10)
            .setArrowPosition(0.5f)
            .setPadding(12)
            .setCornerRadius(8f)
            .setBackgroundColorResource(R.color.white)
            .setBalloonAnimation(BalloonAnimation.ELASTIC)
//                    .setLifecycleOwner(context.ac.lifecycle)
            .build()

        balloon.showAlignTop(it)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View?
        val binding = DataCardBinding.inflate(LayoutInflater.from(context), parent, false)
            view = binding.root
        return ViewHolder(binding, view)
    }
    override fun getItemCount(): Int {
       return list.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(list[position]!= null){
            holder.bind(list[position]!!)
        }
    }
}





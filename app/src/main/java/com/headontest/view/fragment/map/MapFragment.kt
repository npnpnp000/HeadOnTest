package com.headontest.view.fragment.map

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.headontest.R
import com.headontest.application.UserApplication
import com.headontest.databinding.AncorPointBinding
import com.headontest.databinding.FragmentMapBinding
import com.headontest.viewModel.MapViewModel
import com.headontest.viewModel.ViewModelFactory
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.extension.localization.localizeLabels
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.viewannotation.ViewAnnotationManager
import com.mapbox.maps.viewannotation.viewAnnotationOptions
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip
import java.util.*


class MapFragment : Fragment() {

     var binding: FragmentMapBinding? =null

    lateinit var mapViewModel: MapViewModel
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var mapboxMap: MapboxMap
    private lateinit var viewAnnotationManager: ViewAnnotationManager
     var mapView: MapView? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userApplication = requireActivity().application as UserApplication

        viewModelFactory = ViewModelFactory(userApplication.repository,null)
        mapViewModel = ViewModelProvider(this, viewModelFactory).get(MapViewModel::class.java)

//        Mapbox.getInstance(activity?.applicationContext!!,getString(R.string.mapbox_token))


        val circelBm: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.circel)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMapBinding.inflate(inflater, container, false)

//        mainViewModel.insertMessageToRoom(Message((0..100).random().toString()))

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var longitude = 0.0
        var latitude = 0.0

        if (arguments != null) {
            // The getPrivacyPolicyLink() method will be created automatically.
            longitude = requireArguments().getFloat("longitude").toDouble()
            latitude = requireArguments().getFloat("latitude").toDouble()
            Log.e("lonlat","${longitude}, ${latitude}")
        }else{
            Log.e("arguments","null")
        }
        mapView = binding?.mapView

        mapView?.getMapboxMap()?.apply {
            loadStyleUri(Style.MAPBOX_STREETS,{ style ->
                val locale = resources.configuration.locale
                style.localizeLabels(locale)
            })
            // Add the view annotation at the center point
            addViewAnnotation(Point.fromLngLat(longitude, latitude), mapView!!)
        }
//         define camera position
        val cameraPosition = CameraOptions.Builder()
            .zoom(9.0)
            .center(Point.fromLngLat(longitude, latitude))
            .build()
//         set camera position
        mapView?.getMapboxMap()?.setCamera(cameraPosition)
//        addAnnotationToMap(longitude,latitude)

       }
    private fun addViewAnnotation(point: Point, this_mapView: MapView) {
        viewAnnotationManager = this_mapView.viewAnnotationManager
        // Define the view annotation
        val viewAnnotation = viewAnnotationManager.addViewAnnotation(
            // Specify the layout resource id
            resId = R.layout.ancor_point,
            // Set any view annotation options
            options = viewAnnotationOptions {
                geometry(point)
            }
        )
        val balloon = Balloon.Builder(requireContext())
            .setHeight(BalloonSizeSpec.WRAP)
            .setWidth(BalloonSizeSpec.WRAP)
            .setText("address")
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

        AncorPointBinding.bind(viewAnnotation)
        balloon.showAlignTop(viewAnnotation)
    }

    private fun bitmapFromDrawableRes(context: Context, @DrawableRes resourceId: Int) =
        convertDrawableToBitmap(AppCompatResources.getDrawable(context, resourceId))

    private fun convertDrawableToBitmap(sourceDrawable: Drawable?): Bitmap? {
        if (sourceDrawable == null) {
            return null
        }
        return if (sourceDrawable is BitmapDrawable) {
            sourceDrawable.bitmap
        } else {
// copying drawable object to not manipulate on the same reference
            val constantState = sourceDrawable.constantState ?: return null
            val drawable = constantState.newDrawable().mutate()
            val bitmap: Bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth, drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            bitmap
        }
    }
    override fun onStart() {
        super.onStart()
        mapView?.onStart()

    }



    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }
    override fun onDestroy() {
        super.onDestroy()
        binding = null
        mapView?.onDestroy()
    }


    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }
}
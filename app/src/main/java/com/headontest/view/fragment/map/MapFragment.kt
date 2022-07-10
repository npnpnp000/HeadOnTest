package com.headontest.view.fragment.map


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
import com.headontest.databinding.FragmentMapBinding
import com.headontest.viewModel.MapViewModel
import com.headontest.viewModel.ViewModelFactory
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.extension.style.layers.properties.generated.TextAnchor
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.*
import com.mapbox.maps.viewannotation.ViewAnnotationManager

class MapFragment : Fragment() {

    private var mapView: MapView? = null;
    var binding: FragmentMapBinding? = null

    lateinit var mapViewModel: MapViewModel
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewAnnotationManager: ViewAnnotationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userApplication = requireActivity().application as UserApplication

        viewModelFactory = ViewModelFactory(userApplication.repository, null)
        mapViewModel = ViewModelProvider(this, viewModelFactory).get(MapViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMapBinding.inflate(inflater, container, false)


        var longitude = 0.0
        var latitude = 0.0
        var address = ""

        if (arguments != null) {
            longitude = requireArguments().getFloat("longitude").toDouble()
            latitude = requireArguments().getFloat("latitude").toDouble()
            address = requireArguments().getString("address", "")
        } else {
            Log.e("arguments", "null")
        }

        mapView = binding?.mapView

        viewAnnotationManager = binding!!.mapView.viewAnnotationManager

        binding!!.mapView.getMapboxMap().apply {
            // Load a map style
            loadStyleUri(Style.MAPBOX_STREETS) {
                // define camera position
                val cameraPosition = CameraOptions.Builder()
                    .zoom(14.0)
                    .center(Point.fromLngLat(longitude, latitude))
                    .build()
                // set camera position
                this.setCamera(cameraPosition)
                // Add the annotation at the point
                addAnnotation(Point.fromLngLat(longitude, latitude), address)
            }
        }

        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        baseSetting()

    }

    private fun addAnnotation(point: Point, address: String) {
        // Create an instance of the Annotation API and get the CircleAnnotationManager.
        val annotationApi = binding?.mapView?.annotations
        val circleAnnotationManager =
            annotationApi?.createCircleAnnotationManager(binding?.mapView!!)
        // Set options for the resulting circle layer.
        val circleAnnotationOptions: CircleAnnotationOptions = CircleAnnotationOptions()
            // Define a geographic coordinate.
            .withPoint(point)
            // Style the circle that will be added to the map.
            .withCircleRadius(8.0)
            .withCircleColor("#34D078")
            .withCircleStrokeWidth(2.0)
            .withCircleStrokeColor("#ffffff")

        // Create an instance of the Annotation API and get the PointAnnotationManager.
        val pointAnnotationManager = annotationApi?.createPointAnnotationManager(binding?.mapView!!)
        // Set options for the resulting symbol layer.
        val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
            // Define a geographic coordinate.
            .withPoint(point)
            .withTextField(address)
            .withTextSize(30.0)
            .withTextHaloColor("#000000")
            .withTextAnchor(TextAnchor.BOTTOM)

        // Add the resulting circle to the map.
        circleAnnotationManager?.create(circleAnnotationOptions)
        // Add the resulting pointAnnotation to the map.
        pointAnnotationManager?.create(pointAnnotationOptions)

    }
/*    // not work in fragment
 private fun addViewAnnotation(point: Point) {
    // Define the view annotation
    val viewAnnotation = viewAnnotationManager.addViewAnnotation(
        // Specify the layout resource id
        resId = R.layout.annotation_view,
        // Set any view annotation options
        options = viewAnnotationOptions {
            geometry(point)
        }
    )
    AnnotationViewBinding.bind(viewAnnotation)
}*/

    private fun baseSetting() {

        binding?.include?.topFragTxt?.text = getString(R.string.map_title)

        binding?.include?.topCloseImg?.setOnClickListener {
            findNavController().popBackStack()
        }
    }


    @SuppressLint("Lifecycle")
    override fun onStart() {
        super.onStart()
        mapView?.onStart()

    }


    @SuppressLint("Lifecycle")
    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    @SuppressLint("Lifecycle")
    override fun onDestroy() {
        super.onDestroy()
        binding = null
        mapView?.onDestroy()
    }


    @SuppressLint("Lifecycle")
    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }
}
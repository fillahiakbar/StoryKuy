package com.example.fillstoryapp.view.maps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.fillstoryapp.R
import com.example.fillstoryapp.data.response.story.ListStoryItem
import com.example.fillstoryapp.databinding.ActivityMapsBinding
import com.example.fillstoryapp.view.ViewModelFactory
import com.example.fillstoryapp.data.response.Result
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private val viewModel by viewModels<MapsViewModel> {
        ViewModelFactory.getInstance(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        viewModel.getLocations().observe(this) {
            if (it != null) {
                when(it) {
                    is Result.Success -> {
                        addManyMarker(it.data.story)
                    }
                    is Result.Loading -> {}
                    is Result.Error -> {
                        Toast.makeText(this, it.error, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private val boundsBuilder = LatLngBounds.Builder()

    private fun addManyMarker(stories: List<ListStoryItem>) {
        stories.forEach { data ->
            val latLng = LatLng(data.lat, data.lon)
            mMap.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .title(data.name)
                    .snippet(data.description))
            boundsBuilder.include(latLng)
        }
        val bounds: LatLngBounds = boundsBuilder.build()
        mMap.animateCamera(
            CameraUpdateFactory.newLatLngBounds(
                bounds,
                resources.displayMetrics.widthPixels,
                resources.displayMetrics.heightPixels,
                30
            )
        )
    }
}
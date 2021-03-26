package com.example.testvertagelab

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.SimpleAdapter
import com.example.testvertagelab.databinding.ActivityMapsBinding
import com.example.testvertagelab.networkAPI.PlacesLocations

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

const val ID = "id"
const val NAME = "name"

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val email = Intent().getStringExtra("email") ?: ""
        val listPlaces = Intent().getParcelableArrayListExtra<PlacesLocations>("list")
        Log.e("tyi", "email $email list ${listPlaces?.size}")

        val map = mutableMapOf<String, String>()
        val listMap = arrayListOf<MutableMap<String, String>>()
        for (i in listPlaces?.indices ?: listOf<PlacesLocations>()) {
            mMap.addMarker(MarkerOptions().position(LatLng(listPlaces!![i as Int]!!.lat, listPlaces[i]!!.lat)).title(listPlaces[i]?.name))
            map[ID] = listPlaces[i]?.id.toString()
            listPlaces[i]?.name?.let { map.put(NAME, it) }
            listMap.add(map)
        }
        val listAttribute = arrayOf(ID, NAME)
        val listResources = intArrayOf(R.id.textId, R.id.textName)

        val adapter = SimpleAdapter(this, listMap, R.layout.list_item, listAttribute, listResources)

        binding.textToolbar.text = email
        binding.listItem.adapter = adapter
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

        // Add a marker in Sydney and move the camera
       // val sydney = LatLng(-34.0, 151.0)
      //  mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
    }
}
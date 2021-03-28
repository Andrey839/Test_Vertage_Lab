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
    private var location: LatLng = LatLng(0.0,0.0)
    private var tittle = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val email = intent.getStringExtra("email") ?: "?"
        val listPlaces = intent.getParcelableArrayListExtra<PlacesLocations>("list")

        val map = mutableMapOf<String, String>()
        val listMap = arrayListOf<MutableMap<String, String>>()
        for (i in listPlaces?.indices ?: listOf<PlacesLocations>()) {
            // добавляем на карту координаты точки, название, обновляем карту
            location = LatLng(listPlaces!![i as Int]!!.lat, listPlaces[i]!!.lat)
            tittle = listPlaces[i]?.name.toString()
            mapFragment.getMapAsync(this)
            // заполняем списки местами для адаптера
            map[ID] = listPlaces[i]?.id.toString()
            listPlaces[i]?.name?.let { map.put(NAME, it) }
            listMap.add(map)
            Log.e("tyi","li ${listMap[i].values}")
        }
        val listAttribute = arrayOf(ID, NAME)
        val listResources = intArrayOf(R.id.textId, R.id.textName)
        listMap.forEach { Log.e("tyi","map ${it.values}") }
        val adapter = SimpleAdapter(this, listMap, R.layout.list_item, listAttribute, listResources)

        binding.textToolbar.text = email
        // передаём адаптер
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
        mMap.addMarker(MarkerOptions().position(location).title(tittle))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location))
    }
}
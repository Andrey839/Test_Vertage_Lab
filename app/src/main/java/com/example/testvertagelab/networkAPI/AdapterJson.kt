package com.example.testvertagelab.networkAPI

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


data class JsonPlaces(
        val places: List<PlacesLocations>
)
@Parcelize
data class PlacesLocations (
        val id: Int,
        val name: String,
        val lat: Double,
        val lng: Double
        ) : Parcelable

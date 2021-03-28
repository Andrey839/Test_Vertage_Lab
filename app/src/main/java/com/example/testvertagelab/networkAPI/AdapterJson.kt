package com.example.testvertagelab.networkAPI


import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
data class JsonPlaces(
        val places: List<PlacesLocations>
)

@JsonClass(generateAdapter = true)
data class PlacesLocations(
        val id: Int,
        val name: String,
        val lat: Double,
        val lng: Double
): Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readInt(),
                parcel.readString()!!,
                parcel.readDouble(),
                parcel.readDouble()) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeInt(id)
                parcel.writeString(name)
                parcel.writeDouble(lat)
                parcel.writeDouble(lng)
        }

        override fun describeContents(): Int {
                return 0
        }

        companion object CREATOR : Parcelable.Creator<PlacesLocations> {
                override fun createFromParcel(parcel: Parcel): PlacesLocations {
                        return PlacesLocations(parcel)
                }

                override fun newArray(size: Int): Array<PlacesLocations?> {
                        return arrayOfNulls(size)
                }
        }

}
// : Parcelable {
 //   private companion object : Parceler<PlacesLocations> {
 //       override fun create(parcel: Parcel): PlacesLocations {
 //           return PlacesLocations(parcel.readInt(), parcel.readString()!!, parcel.readDouble(), parcel.readDouble())
 //       }
//
 //       override fun PlacesLocations.write(parcel: Parcel, flags: Int) {
 //           parcel.writeInt(id)
 //           parcel.writeString(name)
 //           parcel.writeDouble(lat)
 //           parcel.writeDouble(lng)
 //       }
//
 //   }
//}

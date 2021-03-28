package com.example.testvertagelab.networkAPI

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

const val basicUrl = "https://2fjd9l3x1l.api.quickmocker.com/kyiv/"

interface Places{

    @GET("places/")
    suspend fun getPlaces(): JsonPlaces
}

private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

object Api {
    private val retrofitFilmsNow = Retrofit.Builder()

            .baseUrl(basicUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

    val getData: Places = retrofitFilmsNow.create(Places::class.java)
}
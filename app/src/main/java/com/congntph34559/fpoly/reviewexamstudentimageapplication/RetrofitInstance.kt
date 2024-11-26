package com.congntph34559.fpoly.reviewexamstudentimageapplication

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
//    "https://nominatim.openstreetmap.org/search?q=${query}&format=json&addressdetails=1&limit=5&countrycodes=VN"

    private const val BASE_URL = "https://rsapi.goong.io/"
    private const val BASE_URL2 = "https://nominatim.openstreetmap.org/"



    val api: GoongAPI by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GoongAPI::class.java)
    }
}
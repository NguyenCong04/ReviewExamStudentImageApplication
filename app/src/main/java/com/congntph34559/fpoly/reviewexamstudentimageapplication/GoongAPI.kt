package com.congntph34559.fpoly.reviewexamstudentimageapplication

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.Objects

interface GoongAPI {
//    "https://nominatim.openstreetmap.org/search?q=${query}&format=json&addressdetails=1&limit=5&countrycodes=VN"

    @GET("Place/AutoComplete")
    fun getPlace(
        @Query("api_key") apiKey: String,
        @Query("input") query: String
    ): Call<SuggestionResponse>

    @GET("search")
    fun getPlace2(
        @Query("q") query: String,
        @Query("format") format: String = "json",
        @Query("addressdetails") addressDetails: Int = 1,
        @Query("limit") limit: Int = 5,
        @Query("countrycodes") countryCodes: String = "VN"
    ): Call<SuggestSearchModel>


}
package com.example.ubereatsoffline.network.services

import com.example.ubereatsoffline.models.Restaurant
import com.example.ubereatsoffline.models.RestaurantDetailInfo
import retrofit2.Call
import retrofit2.http.GET

interface RestaurantService {
    @GET("")
    fun getRestaurants(): Call<List<Restaurant>>

    @GET("")
    fun getRestaurantInfo(id: String): Call<RestaurantDetailInfo>
}
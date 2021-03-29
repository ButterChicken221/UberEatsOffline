package com.example.ubereatsoffline.network.services

import com.example.ubereatsoffline.models.BookingInfo
import com.example.ubereatsoffline.models.Restaurant
import com.example.ubereatsoffline.models.RestaurantDetailInfo
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RestaurantService {
    @GET("/restaurant?page=0&size=100")
    fun getRestaurants(): Call<List<Restaurant>>

    @POST("restaurant/reservation")
    fun bookTable(@Body bookingInfo: BookingInfo): Call<JSONObject>
}
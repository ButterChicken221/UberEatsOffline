package com.example.ubereatsoffline.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uberassistant.network.RetrofitClientInstance.retrofitInstance
import com.example.ubereatsoffline.models.BookingInfo
import com.example.ubereatsoffline.models.Restaurant
import com.example.ubereatsoffline.network.services.RestaurantService
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RestaurantViewModel: ViewModel() {

    private val service: RestaurantService = retrofitInstance!!.create(RestaurantService::class.java)

    fun getRestaurants(): MutableLiveData<List<Restaurant>?> {
        val data = MutableLiveData<List<Restaurant>?>()
        val call: Call<List<Restaurant>> = service.getRestaurants()
        call.enqueue(object : Callback<List<Restaurant>> {
            override fun onResponse(call: Call<List<Restaurant>>, response: Response<List<Restaurant>>) {
                data.postValue(response.body())
            }

            override fun onFailure(call: Call<List<Restaurant>>, t: Throwable?) {
                data.postValue(null)
            }
        })
        return data
    }

    fun bookTable(bookingInfo: BookingInfo): MutableLiveData<Boolean> {
        val call = service.bookTable(bookingInfo)
        val data = MutableLiveData<Boolean>()
        call.enqueue(object : Callback<JSONObject> {
            override fun onResponse(call: Call<JSONObject>, response: Response<JSONObject>) {
                data.postValue(true)
            }

            override fun onFailure(call: Call<JSONObject>, t: Throwable) {
                data.postValue(false)
            }
        })
        return data
    }
}
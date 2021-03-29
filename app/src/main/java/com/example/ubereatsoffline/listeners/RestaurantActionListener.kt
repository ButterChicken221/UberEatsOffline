package com.example.ubereatsoffline.listeners

import com.example.ubereatsoffline.models.Restaurant

interface RestaurantActionListener {
    fun onRestaurantClicked(restaurant: Restaurant)
}
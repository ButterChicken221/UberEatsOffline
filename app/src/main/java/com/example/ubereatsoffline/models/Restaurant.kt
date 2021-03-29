package com.example.ubereatsoffline.models

data class Restaurant(
    val id: Int,
    val name: String,
    val rating: Float,
    val openTime: String = "",
    val closingTime: String = "",
    val costForOne: Int = 0,
    val imageUrl: String = "",
    val reservationSlots: List<Slot>? = null
)

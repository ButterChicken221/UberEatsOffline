package com.example.ubereatsoffline.utils

import android.content.Context
import android.content.Intent
import com.example.ubereatsoffline.OneTapRideBookingActivity

object IntentFactory {
    fun getOneTapRideIntent(context: Context, source: String, destination: String, price: Float) =
        Intent(context, OneTapRideBookingActivity::class.java).apply {
            putExtra(Constants.SOURCE, source)
            putExtra(Constants.DESTINATION, destination)
            putExtra(Constants.PRICE, price)
        }
}
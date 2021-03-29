package com.example.ubereatsoffline.utils

import android.content.Context
import android.widget.Toast

object Utils {

    var isRefreshRequired = false

    fun showShortToast(msg: String, context: Context) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    fun showLongToast(msg: String, context: Context) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }
}
package com.example.ubereatsoffline

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ubereatsoffline.adapters.RestaurantDetailAdapter
import com.example.ubereatsoffline.databinding.ActivityRestaurantDetailsBinding
import com.example.ubereatsoffline.listeners.SlotActionListener
import com.example.ubereatsoffline.models.Restaurant
import com.example.ubereatsoffline.models.Slot
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RestaurantDetailsActivity : AppCompatActivity(), SlotActionListener {

    private lateinit var mBinding: ActivityRestaurantDetailsBinding
    private var restaurant: Restaurant? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityRestaurantDetailsBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        intent.extras?.get("restaurant")?.let {
            restaurant = Gson().fromJson(it.toString(), object : TypeToken<Restaurant>() {}.type)
        }

        val listener = this
        restaurant?.reservationSlots?.run {

            mBinding.slotRv.apply {
                layoutManager = GridLayoutManager(this@RestaurantDetailsActivity, 3)
                adapter = RestaurantDetailAdapter(this@run.filter { it.availableCount > 0 }, this@RestaurantDetailsActivity, listener)
            }
        }
    }

    override fun onSlotClicked(slot: Slot) {
        restaurant?.let {
            RestaurantBookingFragment(it, slot, this.application).show(supportFragmentManager, "slot screen")
        }
    }
}
package com.example.ubereatsoffline

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ubereatsoffline.adapters.RestaurantDetailAdapter
import com.example.ubereatsoffline.databinding.ActivityRestaurantDetailsBinding
import com.example.ubereatsoffline.listeners.SlotActionListener
import com.example.ubereatsoffline.models.Slot

class RestaurantDetailsActivity : AppCompatActivity(), SlotActionListener {

    private lateinit var mBinding: ActivityRestaurantDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityRestaurantDetailsBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        val list = listOf(
            Slot("11:00 AM"),
            Slot("12:00 PM"),
            Slot("01:00 PM"),
            Slot("02:00 PM"),
            Slot("03:00 PM"),
            Slot("04:00 PM"),
            Slot("05:00 PM")
        )
        val listener = this
        mBinding.slotRv.apply {
            layoutManager = GridLayoutManager(this@RestaurantDetailsActivity, 3)
            adapter = RestaurantDetailAdapter(list, this@RestaurantDetailsActivity, listener)
        }
    }

    override fun onSlotClicked(slot: Slot) {
        RestaurantBookingFragment().show(supportFragmentManager, "slot screen")
    }
}
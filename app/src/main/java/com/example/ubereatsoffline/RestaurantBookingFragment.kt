package com.example.ubereatsoffline

import android.app.Application
import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.ubereatsoffline.databinding.RestaurantBookingLayoutBinding
import com.example.ubereatsoffline.models.BookingInfo
import com.example.ubereatsoffline.models.Restaurant
import com.example.ubereatsoffline.models.Slot
import com.example.ubereatsoffline.utils.Utils
import com.example.ubereatsoffline.viewmodels.RestaurantViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class RestaurantBookingFragment(private val restaurant: Restaurant, private val slot: Slot, private val application: Application): BottomSheetDialogFragment() {

    private lateinit var mBinding: RestaurantBookingLayoutBinding
    private lateinit var mViewModel: RestaurantViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = RestaurantBookingLayoutBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(RestaurantViewModel::class.java)
        mBinding.restaurantName.text = restaurant.name
        mBinding.restaurantRating.text = restaurant.rating.toString()
        Glide.with(context!!).load(restaurant.imageUrl).into(mBinding.restaurantImage)
        mBinding.bookCta.setOnClickListener {
            mViewModel.bookTable(BookingInfo(restaurant.id, 1, slot.timeSlot)).observe(this, Observer {
                if(it) {
                    mBinding.successLayout.visibility = View.VISIBLE
                    mBinding.anim.playAnimation()
                    Utils.isRefreshRequired = true
                    Handler(Looper.getMainLooper()).postDelayed(Runnable {
                        activity?.onBackPressed()
                    }, 3000)
                }
            })
        }
    }
}
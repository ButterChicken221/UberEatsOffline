package com.example.ubereatsoffline

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.ubereatsoffline.databinding.RestaurantBookingLayoutBinding
import com.example.ubereatsoffline.models.Restaurant
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class RestaurantBookingFragment: BottomSheetDialogFragment() {

    private lateinit var mBinding: RestaurantBookingLayoutBinding

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
        val restaurant = Restaurant("1", "Hauz Khas Social", rating = "4.3", imageUrl = "https://b.zmtcdn.com/data/pictures/7/18037817/235a118e0c275deae64db9a2a4b3d6a2_featured_v2.png")
        mBinding.restaurantName.text = restaurant.name
        mBinding.restaurantRating.text = restaurant.rating.toString()
        Glide.with(context!!).load(restaurant.imageUrl).into(mBinding.restaurantImage)
    }
}
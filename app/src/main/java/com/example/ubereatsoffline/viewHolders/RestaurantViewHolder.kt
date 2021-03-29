package com.example.ubereatsoffline.viewHolders

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ubereatsoffline.databinding.RestaurantItemViewBinding
import com.example.ubereatsoffline.listeners.RestaurantActionListener
import com.example.ubereatsoffline.models.Restaurant

// <a href="https://iconscout.com/icons/uber-eats" target="_blank">Uber-eats Icon</a> on <a href="https://iconscout.com">Iconscout</a>
class RestaurantViewHolder(private val mBinding: RestaurantItemViewBinding): RecyclerView.ViewHolder(mBinding.root) {

    fun bindData(data: Restaurant, context: Context, listener: RestaurantActionListener) {
        mBinding.restaurantName.text = data.name
        mBinding.restaurantRating.text = data.rating.toString()
        Glide.with(context).load(data.imageUrl).into(mBinding.restaurantImage)
        mBinding.root.setOnClickListener {
            listener.onRestaurantClicked(data)
        }
    }
}
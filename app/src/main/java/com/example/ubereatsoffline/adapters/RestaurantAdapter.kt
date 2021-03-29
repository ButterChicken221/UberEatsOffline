package com.example.ubereatsoffline.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ubereatsoffline.databinding.RestaurantItemViewBinding
import com.example.ubereatsoffline.listeners.RestaurantActionListener
import com.example.ubereatsoffline.models.Restaurant
import com.example.ubereatsoffline.viewHolders.RestaurantViewHolder

class RestaurantAdapter(
    private var mList: List<Restaurant>,
    private var mContext: Context,
    private var listener: RestaurantActionListener
    ): RecyclerView.Adapter<RestaurantViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        return RestaurantViewHolder(RestaurantItemViewBinding.inflate(LayoutInflater.from(mContext), parent, false))
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.bindData(mList[position], mContext, listener)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun updateList(list: List<Restaurant>) {
        mList = list
    }

}
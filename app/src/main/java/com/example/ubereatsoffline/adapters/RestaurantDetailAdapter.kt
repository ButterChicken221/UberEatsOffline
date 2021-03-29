package com.example.ubereatsoffline.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ubereatsoffline.R
import com.example.ubereatsoffline.listeners.SlotActionListener
import com.example.ubereatsoffline.models.Slot


class RestaurantDetailAdapter(private val mList: List<Slot>, val context: Context, val listener: SlotActionListener): RecyclerView.Adapter<RestaurantDetailAdapter.RestaurantDetailViewHolder>() {

    inner class RestaurantDetailViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val slotTv: TextView = view.findViewById(R.id.slot)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantDetailViewHolder {
        return RestaurantDetailViewHolder(LayoutInflater.from(context).inflate(R.layout.slot_item_view, parent, false))
    }

    override fun onBindViewHolder(holder: RestaurantDetailViewHolder, position: Int) {

        holder.slotTv.text = getFormattedTime(mList[position].timeSlot)
        holder.slotTv.setOnClickListener {
            listener.onSlotClicked(mList[position])
        }
    }

    private fun getFormattedTime(time: String): String {
        return time.split("T")[1].substring(0,5)
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}
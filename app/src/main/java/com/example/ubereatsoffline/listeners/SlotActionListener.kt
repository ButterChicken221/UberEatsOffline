package com.example.ubereatsoffline.listeners

import com.example.ubereatsoffline.models.Slot

interface SlotActionListener {
    fun onSlotClicked(slot: Slot)
}
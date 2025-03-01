package com.example.stateitemproject.presentation

import androidx.recyclerview.widget.DiffUtil
import com.example.stateitemproject.domain.StateItem

class StateItemDiffCallback:DiffUtil.ItemCallback<StateItem>() {
    override fun areItemsTheSame(oldItem: StateItem, newItem: StateItem): Boolean {
        return oldItem.id==newItem.id
    }

    override fun areContentsTheSame(oldItem: StateItem, newItem: StateItem): Boolean {
        return oldItem==newItem
    }
}
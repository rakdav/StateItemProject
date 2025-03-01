package com.example.stateitemproject.presentation


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.stateitemproject.databinding.StateItemBinding
import com.example.stateitemproject.domain.StateItem

class StateItemAdapter: ListAdapter<StateItem,StateItemViewHolder>(StateItemDiffCallback()) {
    var onStateItemClickListener: ((StateItem) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StateItemViewHolder {
        val itemBinding= StateItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return StateItemViewHolder(itemBinding)
    }
    override fun onBindViewHolder(holder: StateItemViewHolder, position: Int) {
        val stateItem = getItem(position)
        holder.itemBinding.imageView.setImageResource(stateItem.flag)
        holder.itemBinding.textView.text = stateItem.name
        holder.itemBinding.textView2.text = stateItem.capital
        holder.itemView.setOnClickListener {
            onStateItemClickListener?.invoke(stateItem)
        }
    }
}
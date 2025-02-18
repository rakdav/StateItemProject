package com.example.stateitemproject.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.stateitemproject.databinding.StateItemBinding
import com.example.stateitemproject.domain.StateItem

class StateItemAdapter(private var states:List<StateItem>): RecyclerView.Adapter<StateItemAdapter.ViewHolder>() {

    class ViewHolder(val itemBinding: StateItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding= StateItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(itemBinding)
    }
    override fun getItemCount(): Int {
        return states.size;
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val state=states[position]
        holder.itemBinding.imageView.setImageResource(state.flag)
        holder.itemBinding.textView.text=state.name
        holder.itemBinding.textView2.text=state.capital
        holder.itemBinding.cardView.setOnClickListener {

        }
    }
}
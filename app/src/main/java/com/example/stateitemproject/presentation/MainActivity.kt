package com.example.stateitemproject.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stateitemproject.databinding.ActivityMainBinding
import com.example.stateitemproject.domain.StateItem

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var stateListAdapter: StateItemAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel= ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.stateList.observe(this){
            showList(it)
        }
        binding.fab.setOnClickListener {
            val intent = StateItemActivity.newIntentAddItem(this)
            startActivity(intent)
        }

    }
    private fun showList(list:List<StateItem>){
        binding.list.removeAllViews()
        var linearLayoutManager=LinearLayoutManager(this)
        val adapter=StateItemAdapter(list)
        binding.list.layoutManager=linearLayoutManager
        binding.list.adapter=adapter
    }
}
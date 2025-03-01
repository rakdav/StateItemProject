package com.example.stateitemproject.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stateitemproject.R
import com.example.stateitemproject.databinding.ActivityMainBinding
import com.example.stateitemproject.domain.StateItem

class MainActivity : AppCompatActivity(),StateItemFragment.OnEditingFinishedListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var stateListAdapter: StateItemAdapter
    private var stateItemContainer: FragmentContainerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        stateItemContainer=binding.stateItemContainer
        setupRecyclerView()
        viewModel= ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.stateList.observe(this){
            stateListAdapter.submitList(it)
        }
        binding.fab.setOnClickListener {
            if (isOnePaneMode()) {
                val intent = StateItemActivity.newIntentAddItem(this)
                startActivity(intent)
            } else {
                launchFragment(StateItemFragment.newInstanceAddItem())
            }
        }
    }
    override fun onEditingFinished() {
        supportFragmentManager.popBackStack()
    }
    private fun isOnePaneMode(): Boolean {
        return stateItemContainer == null
    }
    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.state_item_container, fragment)
            .addToBackStack(null)
            .commit()
    }
    private fun setupRecyclerView() {
        val rvShopList = binding.list
        with(rvShopList) {
            stateListAdapter = StateItemAdapter()
            adapter = stateListAdapter
        }
        setupClickListener()
        setupSwipeListener(rvShopList)
    }
    private fun setupSwipeListener(rvShopList: RecyclerView) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = stateListAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteStateItem(item)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvShopList)
    }
    private fun setupClickListener() {
        stateListAdapter.onStateItemClickListener = {
            if (isOnePaneMode()) {
                val intent = StateItemActivity.newIntentEditItem(this, it.id)
                startActivity(intent)
            } else {
                launchFragment(StateItemFragment.newInstanceEditItem(it.id))
            }
        }
    }
}
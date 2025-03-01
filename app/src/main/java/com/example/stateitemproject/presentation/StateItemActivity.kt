package com.example.stateitemproject.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.stateitemproject.R
import com.example.stateitemproject.databinding.ActivityStateItemBinding

class StateItemActivity : AppCompatActivity(),StateItemFragment.OnEditingFinishedListener {
    private lateinit var viewModel: StateItemViewModel
    private lateinit var binding: ActivityStateItemBinding
    private var screenMode=MODE_UNKNOWN
    private var stateItemId=-1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityStateItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        parseIntent()
        if (savedInstanceState == null) {
            launchRightMode()
        }
    }
    private fun launchRightMode() {
        val fragment = when (screenMode) {
            MODE_EDIT -> StateItemFragment.newInstanceEditItem(stateItemId)
            MODE_ADD  -> StateItemFragment.newInstanceAddItem()
            else      -> throw RuntimeException("Unknown screen mode $screenMode")
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.state_item_container, fragment)
            .commit()
    }

    private fun parseIntent(){
        if(!intent.hasExtra(EXTRA_SCREEN_MODE)){
            throw RuntimeException("Param screen mode is absent")
        }
        val mode=intent.getStringExtra(EXTRA_SCREEN_MODE)
        if(mode!= MODE_EDIT&&mode!= MODE_ADD){
            throw RuntimeException("Unknown screen mode $mode")
        }
        if (mode != null) {
            screenMode=mode
            if(screenMode== MODE_EDIT){
                if(!intent.hasExtra(EXTRA_STATE_ITEM_ID)) {
                    throw RuntimeException("Param state item id is absent")
                }
                stateItemId=intent.getIntExtra(EXTRA_STATE_ITEM_ID,0)
            }
        }

    }
    companion object{
        private const val EXTRA_SCREEN_MODE="extra_mode"
        private const val EXTRA_STATE_ITEM_ID="extra_state_item_id"
        private const val MODE_EDIT="mode_edit"
        private const val MODE_ADD="mode_add"
        private const val MODE_UNKNOWN=""
        fun newIntentAddItem(context:Context):Intent{
            val intent=Intent(context,StateItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }
        fun newIntentEditItem(context:Context,stateItemId:Int):Intent{
            val intent=Intent(context,StateItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_STATE_ITEM_ID,stateItemId)
            return intent
        }
    }

    override fun onEditingFinished() {
        finish()
    }
}
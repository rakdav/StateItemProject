package com.example.stateitemproject.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.stateitemproject.databinding.ActivityStateItemBinding

class StateItemActivity : AppCompatActivity() {
    private lateinit var viewModel: StateItemViewModel
    private lateinit var binding: ActivityStateItemBinding
    private var screenMode=MODE_UNKNOWN
    private var stateItemId=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityStateItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        parseIntent()
        viewModel=ViewModelProvider(this)[StateItemViewModel::class.java]
        AddTextChangedListeners()
        launchRightMode()
        observeViewModel()
    }
    private fun AddTextChangedListeners(){
        binding.etName.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputName()
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })
        binding.etCapital.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputCapital()
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })
    }
    private fun observeViewModel(){
        viewModel.errorInputName.observe(this){
            val mes=if(it){
                "Is not correct data"
            }
            else null
            binding.tilName.error=mes
        }
        viewModel.errorInputCapital.observe(this){
            val mes=if(it){
                "Is not correct data"
            }
            else null
            binding.tilCapital.error=mes
        }
        viewModel.shouldCloseScreen.observe(this) {
            finish()
        }
    }
    private fun launchRightMode(){
        when(screenMode){
            MODE_EDIT->launchEditMode()
            MODE_ADD->launchAddMode()
        }
    }
    private fun launchEditMode(){
        viewModel.getStateItem(stateItemId)
        viewModel.stateItem.observe(this){
            binding.etName.setText(it.name)
            binding.etCapital.setText(it.capital)
        }
        binding.saveButton.setOnClickListener {
            viewModel.editStateItem(binding.etName.text.toString(),
                binding.etCapital.text.toString())
        }
    }
    private fun launchAddMode(){
        binding.saveButton.setOnClickListener {
            viewModel.addStateItem(binding.etName.text.toString(),binding.etCapital.text.toString())
        }
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
}
package com.example.stateitemproject.presentation

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stateitemproject.R
import com.example.stateitemproject.data.StateListRepositoryImpl
import com.example.stateitemproject.domain.AddStateItemUseCase
import com.example.stateitemproject.domain.EditStateItemUseCase
import com.example.stateitemproject.domain.GetStateItemUseCase
import com.example.stateitemproject.domain.GetStateListUseCase
import com.example.stateitemproject.domain.StateItem

class StateItemViewModel:ViewModel() {
    private val repository=StateListRepositoryImpl
    private val getStateItemUseCase=GetStateItemUseCase(repository)
    private val addStateItemUseCase=AddStateItemUseCase(repository)
    private val editStateItemUseCase=EditStateItemUseCase(repository)
    private val getStateListUseCase=GetStateListUseCase(repository)


    private val _errorInputName=MutableLiveData<Boolean>()
    val errorInputName:LiveData<Boolean>
        get()=_errorInputName

    private val _errorInputCapital=MutableLiveData<Boolean>()
    val errorInputCapital:LiveData<Boolean>
        get()=_errorInputCapital

    private val _stateItem=MutableLiveData<StateItem>()
    val stateItem:LiveData<StateItem>
        get()=_stateItem

    private val _shouldCloseScreen=MutableLiveData<Unit>()
    val shouldCloseScreen:LiveData<Unit>
        get()=_shouldCloseScreen

    fun getStateItem(stateItemId:Int){
        val item=getStateItemUseCase.getStateItem(stateItemId)
        _stateItem.value=item
    }
    fun addStateItem(inputName:String?,inputCapital:String?){
        val name=parseString(inputName)
        val capital=parseString(inputCapital)
        val fieldsValue=validateInput(name,capital)
        if(fieldsValue){
            if (getStateListUseCase.getStateList().value!=null) {
                val id = getStateListUseCase.getStateList().value!!.size
                val stateItem = StateItem(id + 1, name, capital, R.drawable.russia)
                addStateItemUseCase.addStateItem(stateItem)
            }
            else
            {
                val stateItem = StateItem(1, name, capital, R.drawable.russia)
                addStateItemUseCase.addStateItem(stateItem)
            }
            finishWork()
        }
    }
    fun editStateItem(inputName:String?,inputCapital:String?){
        val name=parseString(inputName)
        val capital=parseString(inputCapital)
        val fieldsValue=validateInput(name,capital)
        if(fieldsValue){
            _stateItem.value?.let {
                val item=it.copy(name=name, capital = capital)
                editStateItemUseCase.editStateItem(item)
                finishWork()
            }
        }
    }

    private fun parseString(str:String?):String{
        return  str?.trim()?:""
    }
    private fun validateInput(name:String,capital:String):Boolean{
        var result=true
        if(name.isBlank()){
            _errorInputName.value=true
            result=false
        }
        if (capital.isBlank()){
            _errorInputCapital.value=true
            result=false
        }
        return result
    }
    fun resetErrorInputName(){
        _errorInputName.value=false
    }
    fun resetErrorInputCapital(){
        _errorInputCapital.value=false
    }
    private fun finishWork()
    {
        _shouldCloseScreen.value=Unit
    }
}
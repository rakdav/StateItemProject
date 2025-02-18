package com.example.stateitemproject.presentation

import androidx.lifecycle.ViewModel
import com.example.stateitemproject.data.StateListRepositoryImpl
import com.example.stateitemproject.domain.DeleteStateItemUseCase
import com.example.stateitemproject.domain.EditStateItemUseCase
import com.example.stateitemproject.domain.GetStateListUseCase
import com.example.stateitemproject.domain.StateItem

class MainViewModel:ViewModel() {
    private val repository=StateListRepositoryImpl
    private val getStateListUseCase=GetStateListUseCase(repository)
    private val deleteStateItemUseCase=DeleteStateItemUseCase(repository)
    private val editStateItemUseCase=EditStateItemUseCase(repository)

    val stateList=getStateListUseCase.getStateList()

    fun deleteStateItem(state: StateItem){
        deleteStateItemUseCase.deleteStateItem(state)
    }
    fun changeState(state: StateItem){
        editStateItemUseCase.editStateItem(state)
    }
}
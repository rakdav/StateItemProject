package com.example.stateitemproject.domain

import androidx.lifecycle.LiveData

interface StateListRepository {
    fun addStateItem(stateItem:StateItem)
    fun deleteStateItem(stateItem:StateItem)
    fun editStateItem(stateItem:StateItem)
    fun getStateItem(stateItemId:Int):StateItem
    fun getStateList():LiveData<List<StateItem>>
}
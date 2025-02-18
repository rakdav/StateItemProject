package com.example.stateitemproject.domain

import androidx.lifecycle.LiveData

class GetStateListUseCase(private val stateListRepository: StateListRepository)
{
    fun getStateList():LiveData<List<StateItem>>{
        return stateListRepository.getStateList()
    }
}
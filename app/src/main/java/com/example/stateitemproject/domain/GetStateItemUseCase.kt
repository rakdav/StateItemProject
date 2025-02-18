package com.example.stateitemproject.domain

class GetStateItemUseCase(private val stateListRepository: StateListRepository)
{
    fun getStateItem(stateItemId:Int):StateItem{
        return stateListRepository.getStateItem(stateItemId)
    }
}
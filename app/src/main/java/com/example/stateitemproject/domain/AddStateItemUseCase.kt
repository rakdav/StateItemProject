package com.example.stateitemproject.domain

class AddStateItemUseCase(private val stateListRepository: StateListRepository) {
    fun addStateItem(stateItem:StateItem){
        stateListRepository.addStateItem(stateItem)
    }
}
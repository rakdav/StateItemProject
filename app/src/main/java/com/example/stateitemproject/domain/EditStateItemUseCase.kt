package com.example.stateitemproject.domain

class EditStateItemUseCase(private val stateListRepository: StateListRepository) {
    fun editStateItem(stateItem:StateItem){
        stateListRepository.editStateItem(stateItem)
    }
}
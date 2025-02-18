package com.example.stateitemproject.domain

class DeleteStateItemUseCase(private val stateListRepository: StateListRepository) {
    fun deleteStateItem(stateItem:StateItem){
        stateListRepository.deleteStateItem(stateItem)
    }
}
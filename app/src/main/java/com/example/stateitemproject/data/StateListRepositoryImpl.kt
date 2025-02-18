package com.example.stateitemproject.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.stateitemproject.R
import com.example.stateitemproject.domain.StateItem
import com.example.stateitemproject.domain.StateListRepository

object StateListRepositoryImpl:StateListRepository {
    private val stateList= sortedSetOf<StateItem>({o1,o2->o1.id.compareTo(o2.id)})
    private val stateListID=MutableLiveData<List<StateItem>>()
    init {
        for(i in 1 until 10){
            val item=StateItem(i,"State $i","Capital $i",R.drawable.russia)
            addStateItem(item)
        }
    }
    override fun addStateItem(stateItem: StateItem) {
        stateList.add(stateItem)
        updateList()
    }
    override fun deleteStateItem(stateItem: StateItem) {
        stateList.remove(stateItem)
        updateList()
    }
    override fun editStateItem(stateItem: StateItem) {
        val oldState= getStateItem(stateItem.id)
        stateList.remove(oldState)
        addStateItem(stateItem)
    }
    override fun getStateItem(stateItemId: Int): StateItem {
        return stateList.find {
            it.id==stateItemId
        }?:throw RuntimeException("Element not found")
    }
    override fun getStateList(): LiveData<List<StateItem>> {
        return stateListID
    }
    private fun updateList(){
        stateListID.value= stateList.toList()
    }
}
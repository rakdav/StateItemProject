package com.example.stateitemproject.data

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.stateitemproject.domain.StateItem
import com.example.stateitemproject.domain.StateListRepository
import com.example.stateitemproject.presentation.MainApplication
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object StateListRepositoryImpl:StateListRepository {
    private var stateList= sortedSetOf<StateItem>({ o1, o2->o1.id.compareTo(o2.id)})
    private val stateListID=MutableLiveData<List<StateItem>>()
    private const val CUSTOM_PREF_NAME = "States_data"
    private const val STATES="states"
    private val prefs:SharedPreferences;

    init {
        val context = MainApplication.appContext
        prefs=customPreferences(context, CUSTOM_PREF_NAME)
        val stringStates= prefs.States
        val type=object:TypeToken<ArrayList<StateItem>>(){}.type
        var list:List<StateItem> = emptyList<StateItem>()
            if(stringStates!="") {
                list= Gson().fromJson(stringStates, type)
                for (i in list) {
                    val item = StateItem(i.id, i.name, i.capital, i.flag)
                    addStateItem(item)
                }
            }
    }
    override fun addStateItem(stateItem: StateItem) {
        stateList.add(stateItem)
        val json: String = Gson().toJson(stateList)
        prefs.States= json
        updateList()
    }
    override fun deleteStateItem(stateItem: StateItem) {
        stateList.remove(stateItem)
        val json: String = Gson().toJson(stateList)
        prefs.States= json
        updateList()
    }
    override fun editStateItem(stateItem: StateItem) {
        val oldState= getStateItem(stateItem.id)
        stateList.remove(oldState)
        val json: String = Gson().toJson(stateList)
        prefs.States= json
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

    private fun customPreferences(context: Context, name:String):SharedPreferences=
        context.getSharedPreferences(name, Context.MODE_PRIVATE)
    private inline fun SharedPreferences.editMe(operation:(SharedPreferences.Editor)->Unit){
        val editMe=edit()
        operation(editMe)
        editMe.apply()
    }
    var SharedPreferences.States
        get() = getString(STATES,"")
        set(value){
            editMe {
                it.putString(STATES,value)
            }
        }
}
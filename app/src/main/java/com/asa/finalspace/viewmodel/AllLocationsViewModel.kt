package com.asa.finalspace.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.asa.finalspace.model.dto_model.TaskFail
import com.asa.finalspace.model.dto_model.TaskResults
import com.asa.finalspace.model.dto_model.TaskSuccess
import com.asa.finalspace.model.locations.GetAllLocations
import com.asa.finalspace.repository.AllLocationsRepository
import kotlinx.coroutines.launch


class AllLocationsViewModel(application:Application,private val allLocationsRepository:AllLocationsRepository): AndroidViewModel(application) {

    private val _locations = MutableLiveData<GetAllLocations>()
    val locationsList:LiveData<GetAllLocations> = _locations
    private val _toastMessage = MutableLiveData<String>()
    val toastMessage:LiveData<String> = _toastMessage
    private var state:ModelSate = LoadedModelState

    init {
        viewModelScope.launch {
            loadLocations()
        }
    }

    suspend fun loadLocations(){
        if (state !is LoadedModelState) return

        try{
            state = LoadedModelState
            val fetchResult:TaskResults<GetAllLocations> = allLocationsRepository.fetchAllLocations()
            when (fetchResult){
                is TaskFail<*> -> _toastMessage.value = fetchResult.message
                is TaskSuccess<GetAllLocations> -> {
                    _locations.value = fetchResult.data
                }
            }

        }finally {
            state = LoadedModelState
        }
    }

}
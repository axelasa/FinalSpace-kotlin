package com.asa.finalspace.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asa.finalspace.allEpisodes
import com.asa.finalspace.model.dto_model.TaskFail
import com.asa.finalspace.model.dto_model.TaskResults
import com.asa.finalspace.model.dto_model.TaskSuccess
import com.asa.finalspace.model.episodes.GetAllEpisodes
import com.asa.finalspace.repository.AllEpisodesRepository
import kotlinx.coroutines.launch

class AllEpisodesViewModel(application: Application,private val allEpisodesRepository:AllEpisodesRepository): AndroidViewModel(application){
    private val _episodeList = MutableLiveData<GetAllEpisodes>()
    val episodeList:LiveData<GetAllEpisodes> = _episodeList
    private val _toastMessage = MutableLiveData<String>()
    val toastMessage:LiveData<String> = _toastMessage
    private var state:ModelSate = LoadedModelState

    init {
        viewModelScope.launch {
            loadEpisodes()
        }
    }

    suspend fun loadEpisodes(){
        if(state !is  LoadedModelState) return

        try {
            state = LoadingModelState
            val fetchResult:TaskResults<GetAllEpisodes> = allEpisodesRepository.getAllEpisodes()
            when(fetchResult){
                is TaskFail<*> -> _toastMessage.value = fetchResult.message
                is TaskSuccess<GetAllEpisodes> -> {
                    _episodeList.value = fetchResult.data
                }
            }
        }finally {
            state = LoadedModelState
        }
    }
}
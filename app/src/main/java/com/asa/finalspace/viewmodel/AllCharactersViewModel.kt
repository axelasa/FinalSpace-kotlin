package com.asa.finalspace.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asa.finalspace.model.characters.GetAllCharacters
import com.asa.finalspace.model.characters.GetAllCharactersItem
import com.asa.finalspace.model.dto_model.TaskFail
import com.asa.finalspace.model.dto_model.TaskResults
import com.asa.finalspace.model.dto_model.TaskSuccess
import com.asa.finalspace.repository.AllCharacterRepository
import com.asa.finalspace.toGetAllCharactersItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AllCharactersViewModel(private val allCharactersRepository: AllCharacterRepository): ViewModel() {

    private val _characterList = MutableStateFlow<GetAllCharacters>(GetAllCharacters() )
    val characterList : StateFlow<GetAllCharacters> =_characterList.asStateFlow()
    private val  _toastMessage =MutableStateFlow<String ?>(null)
    val toastMessage :StateFlow<String?> = _toastMessage
    private var state: ModelSate = LoadedModelState

    init {
        viewModelScope.launch {
            loadCharacters()
        }
    }
        suspend fun loadCharacters() {
            if (state !is LoadedModelState) return

            try {
                state = LoadingModelState
                val fetchedResults: TaskResults<GetAllCharacters> =
                    allCharactersRepository.fetchAllCharacters()
                when(fetchedResults){

                    is TaskFail<*> -> _toastMessage.value = fetchedResults.message
                    is TaskSuccess<GetAllCharacters> -> {
                        _characterList.value = fetchedResults.data//correct this here
                    }
                }

            }finally {
                state = LoadedModelState
            }
        }
}
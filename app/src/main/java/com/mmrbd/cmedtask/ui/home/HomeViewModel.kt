package com.mmrbd.cmedtask.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmrbd.cmedtask.data.model.CharacterModel
import com.mmrbd.cmedtask.data.repository.Repository
import com.mmrbd.cmedtask.utils.AppLogger
import com.mmrbd.cmedtask.utils.network.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private var repository: Repository
) : ViewModel() {

    var characterDataShareFlow = MutableStateFlow<Result<CharacterModel>>(Result.Loading())

    init {
        AppLogger.log("HomeViewModel Init")
        getCharacterData()
    }

    private fun getCharacterData() {
        viewModelScope.launch {
            repository.geQuestions().collect {
                characterDataShareFlow.emit(it)
            }
        }

    }

}
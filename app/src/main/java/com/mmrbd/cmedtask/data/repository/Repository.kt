package com.mmrbd.cmedtask.data.repository

import com.mmrbd.cmedtask.data.model.CharacterModel
import com.mmrbd.cmedtask.utils.network.Result
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun geQuestions(): Flow<Result<CharacterModel>>
}
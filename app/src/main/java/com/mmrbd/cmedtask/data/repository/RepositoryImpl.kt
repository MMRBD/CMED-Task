package com.mmrbd.cmedtask.data.repository

import com.mmrbd.cmedtask.data.api.ApiService
import com.mmrbd.cmedtask.data.model.CharacterModel
import com.mmrbd.cmedtask.utils.network.Failure
import com.mmrbd.cmedtask.utils.network.Result
import com.mmrbd.cmedtask.utils.network.getErrorTypeByHTTPCode
import com.mmrbd.cmedtask.utils.AppLogger
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.net.UnknownHostException
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : Repository {
    override fun geQuestions(): Flow<Result<CharacterModel>> = callbackFlow {
        trySend(Result.Loading())

        try {
            val result = apiService.getQuestions()
            if (result.isSuccessful) {

                trySend(Result.Success(result.body()!!))

            } else {
                trySend(
                    Result.Error(
                        getErrorTypeByHTTPCode(result.code()), null
                    )
                )
            }
        } catch (exception: Throwable) {
            when (exception) {
                is UnknownHostException -> {
                    trySend(Result.Error((Failure.HTTP.NetworkConnection)))
                }

                else -> {
                    trySend(Result.Error(Failure.Exception(exception), null))
                }
            }
        }
        awaitClose()
    }
}
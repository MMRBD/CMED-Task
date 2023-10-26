package com.mmrbd.cmedtask.data.api

import com.mmrbd.cmedtask.data.model.CharacterModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("characters")
    suspend fun getQuestions(): Response<CharacterModel>
}
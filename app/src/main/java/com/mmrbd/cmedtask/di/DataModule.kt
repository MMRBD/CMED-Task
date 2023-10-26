package com.mmrbd.cmedtask.di

import com.mmrbd.cmedtask.data.api.ApiService
import com.mmrbd.cmedtask.data.repository.Repository
import com.mmrbd.cmedtask.data.repository.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun provideRepository(apiService: ApiService): Repository = RepositoryImpl(apiService)

}
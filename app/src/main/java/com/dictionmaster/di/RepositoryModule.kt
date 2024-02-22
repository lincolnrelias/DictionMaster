package com.dictionmaster.di

import android.app.Application
import com.dictionmaster.data.remote.DictionaryApiService
import com.dictionmaster.data.repository.DictionaryRepository
import com.dictionmaster.utils.ApiCallManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDictionaryRepository(apiService: DictionaryApiService): DictionaryRepository {
        return DictionaryRepository(apiService)
    }
    @Provides
    @Singleton
    fun provideApiCallManager(application: Application): ApiCallManager {
        return ApiCallManager(application)
    }
}

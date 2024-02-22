package com.dictionmaster.di

import android.app.Application
import com.dictionmaster.data.interceptors.DictionaryCacheInterceptor
import com.dictionmaster.data.remote.DictionaryApi
import com.dictionmaster.data.remote.DictionaryApiService
import com.dictionmaster.utils.ApiCallManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideDictionaryApiService(okHttpClient: OkHttpClient): DictionaryApiService {
        return DictionaryApiService(okHttpClient)
    }

    @Provides
    @Singleton
    fun provideDictionaryApi(dictionaryApiService: DictionaryApiService): DictionaryApi {
        return dictionaryApiService.createApi()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(cache: Cache, apiCallManager: ApiCallManager): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(DictionaryCacheInterceptor(apiCallManager))
            .build()
    }

    @Provides
    @Singleton
    fun provideCache(application: Application): Cache {
        val cacheSize = (5 * 1024 * 1024).toLong() // 5 MB
        val cacheDirectory = File(application.cacheDir, "http-cache")
        return Cache(cacheDirectory, cacheSize)
    }
}
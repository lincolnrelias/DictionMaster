package com.dictionmaster.data.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DictionaryApiService(private val okHttpClient: OkHttpClient) {
    private val baseUrl = "https://api.dictionaryapi.dev/api/v2/"

    fun createApi(): DictionaryApi {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(DictionaryApi::class.java)
    }
}
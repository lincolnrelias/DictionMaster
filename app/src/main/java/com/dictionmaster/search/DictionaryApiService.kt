package com.dictionmaster.search

import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


interface DictionaryApiService {
    companion object {
        private lateinit var okHttpClient: OkHttpClient

        fun setOkHttpClient(client: OkHttpClient) {
            okHttpClient = client
        }
        fun getOkHttpClient(): OkHttpClient {
            return okHttpClient
        }
        fun create(): DictionaryApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.dictionaryapi.dev/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

            return retrofit.create(DictionaryApiService::class.java)
        }
    }

    @GET("entries/en/{word}")
    fun getWordDetails(
        @Path("word") word: String
    ): Call<List<DictionaryResponseModel>>
}
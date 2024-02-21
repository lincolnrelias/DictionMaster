package com.dictionmaster.data.remote

import com.dictionmaster.data.models.DictionaryResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryApi {
    @GET("entries/en/{word}")
    fun getWordDetails(
        @Path("word") word: String
    ): Call<List<DictionaryResponseModel>>
}
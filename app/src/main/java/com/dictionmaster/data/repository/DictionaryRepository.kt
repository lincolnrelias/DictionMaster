package com.dictionmaster.data.repository

import com.dictionmaster.data.remote.DictionaryApiService
import com.dictionmaster.data.models.DictionaryResponseModel
import com.dictionmaster.domain.repository.IDictionaryRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DictionaryRepository(private val apiService: DictionaryApiService): IDictionaryRepository {

    override fun getWordDetails(word: String, callback: (Result<Response<List<DictionaryResponseModel>>>) -> Unit) {
        val call = apiService.createApi().getWordDetails(word)
        call.enqueue(object : Callback<List<DictionaryResponseModel>> {
            override fun onResponse(
                call: Call<List<DictionaryResponseModel>>,
                response: Response<List<DictionaryResponseModel>>
            ) {
                callback(Result.success(response))
            }

            override fun onFailure(call: Call<List<DictionaryResponseModel>>, t: Throwable) {
                callback(Result.failure(t))
            }
        })
    }
}
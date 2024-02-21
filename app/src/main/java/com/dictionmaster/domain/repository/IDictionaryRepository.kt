package com.dictionmaster.domain.repository

import com.dictionmaster.data.models.DictionaryResponseModel
import retrofit2.Response

interface IDictionaryRepository {
    fun getWordDetails(word: String, callback: (Result<Response<List<DictionaryResponseModel>>>) -> Unit)
}

package com.dictionmaster.presentation.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dictionmaster.data.models.DictionaryResponseModel
import com.dictionmaster.data.repository.DictionaryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: DictionaryRepository) : ViewModel() {

    private val _searchResults = MutableLiveData<Result<Response<List<DictionaryResponseModel>>>?>()
    val searchResults: MutableLiveData<Result<Response<List<DictionaryResponseModel>>>?> = _searchResults

    fun searchWord(word: String) {
        repository.getWordDetails(word) { result ->
            _searchResults.postValue(result)
        }
    }
    fun resetData() {
        _searchResults.value = null
    }
}
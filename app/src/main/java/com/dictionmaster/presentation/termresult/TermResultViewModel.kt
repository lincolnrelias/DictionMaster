package com.dictionmaster.presentation.termresult

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dictionmaster.data.models.DictionaryResponseModel

class TermResultViewModel : ViewModel() {
    private val _data = MutableLiveData<DictionaryResponseModel>()
    val data: LiveData<DictionaryResponseModel> = _data

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun setData(dictionaryData: DictionaryResponseModel) {
        _data.value = dictionaryData
    }

    fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }

}

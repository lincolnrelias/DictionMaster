package com.dictionmaster.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dictionmaster.data.repository.DictionaryRepository
import com.dictionmaster.presentation.search.SearchViewModel

class SearchViewModelFactory(private val repository: DictionaryRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

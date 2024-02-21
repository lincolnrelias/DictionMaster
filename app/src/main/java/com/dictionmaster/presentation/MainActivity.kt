package com.dictionmaster.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dictionmaster.R
import com.dictionmaster.presentation.search.SearchFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        loadSearchFragment()
    }

    private fun loadSearchFragment(){
        val searchFragment = SearchFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(android.R.id.content, searchFragment)
        transaction.commit()
    }

}
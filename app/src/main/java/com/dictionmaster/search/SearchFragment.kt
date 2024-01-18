package com.dictionmaster.search


import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.dictionmaster.PurchaseFragment
import com.dictionmaster.R
import com.dictionmaster.databinding.SearchFragmentBinding
import kotlinx.coroutines.launch

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment() {
    private lateinit var binding: SearchFragmentBinding
    private val apiCallManager by lazy { ApiCallManager(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

         binding =  DataBindingUtil.inflate(
             inflater, R.layout.search_fragment, container, false
         )
        binding.btnSearch.setOnClickListener{
            lifecycleScope.launch {
                makeApiRequest(binding.etTerm.text.toString())
            }

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.etTerm.requestFocus()
    }
        private fun makeApiRequest(word: String) {
        val apiService = DictionaryApiService.create()
        val call = apiService.getWordDetails(word)
        call.enqueue(object : Callback<List<DictionaryResponseModel>> {
            override fun onResponse(call: Call<List<DictionaryResponseModel>>, response: Response<List<DictionaryResponseModel>>) {
                if (response.isSuccessful) {
                    //response didn't come exclusively from cache
                    if(response.raw().networkResponse!=null){
                        apiCallManager.updateCallCountAndTimestamp()
                    }
                    val data = response.body()
                    if (!data.isNullOrEmpty()) {
                        val firstItem = data[0]
                        showResultFragment(firstItem)
                    }
                } else if(!apiCallManager.canMakeApiCall() && response.code()==504) {
                    showPurchaseFragment()
                } else{
                    showErrorDialog("Request Error: "+response.code(),"check for misspelling and non-alphabetic characters")
                }
            }

            override fun onFailure(call: Call<List<DictionaryResponseModel>>, t: Throwable) {
                t.message?.let { showErrorDialog("Request Error: ", it) }
            }
        })
    }
    fun showErrorDialog(title: String,message: String) {
        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                // Handle positive button click if needed
                dialog.dismiss()
            }
            .show()
    }
    private fun showResultFragment(data: DictionaryResponseModel) {
        val resultFragment = TermResultFragment.newInstance(data)

        val transaction = activity?.supportFragmentManager?.beginTransaction()
        if (transaction != null) {
            transaction.replace(android.R.id.content, resultFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

    }
    private fun showPurchaseFragment() {
        val purchaseFragment = PurchaseFragment()

        val transaction = activity?.supportFragmentManager?.beginTransaction()
        if (transaction != null) {
            transaction.replace(android.R.id.content, purchaseFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

    }

}

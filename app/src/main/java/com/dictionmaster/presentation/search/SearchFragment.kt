package com.dictionmaster.presentation.search

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.dictionmaster.presentation.purchase.PurchaseFragment
import com.dictionmaster.R
import com.dictionmaster.data.models.DictionaryResponseModel
import com.dictionmaster.databinding.SearchFragmentBinding
import com.dictionmaster.utils.ApiCallManager
import com.dictionmaster.presentation.termresult.TermResultFragment
import com.dictionmaster.presentation.termresult.TermResultViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var binding: SearchFragmentBinding
    private val apiCallManager by lazy { ApiCallManager(requireContext()) }
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.search_fragment, container, false
        )
        binding.btnSearch.setOnClickListener {
            lifecycleScope.launch {
                makeApiRequest1(binding.etTerm.text.toString())
            }

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.resetData()
        super.onViewCreated(view, savedInstanceState)
        setupObserver()

    }
    private fun setupObserver() {
        viewModel.searchResults.observe(viewLifecycleOwner, Observer { result ->
            result?.onSuccess { response ->
                if (response.isSuccessful) {
                    if(response.raw().cacheResponse==null){
                        apiCallManager.updateCallCountAndTimestamp()
                    }
                    val data = response.body()
                    if (!data.isNullOrEmpty()) {
                        val firstItem = data[0]
                        showResultFragment(firstItem)
                    }
                } else if (!apiCallManager.canMakeApiCall() && response.code() == 504) {
                    showPurchaseFragment()
                } else {
                    showErrorDialog(
                        "Request Error: " + response.code(),
                        "check for misspelling and non-alphabetic characters"
                    )
                }
                isLoading(false)
            }?.onFailure { error ->
                error.message?.let { showErrorDialog("Request Error: ", it) }
                isLoading(false)
            }
        })
    }

    override fun onResume() {
        //TODO check if should happen only on backpress
        super.onResume()
//        binding.etTerm.text?.clear()
//        binding.etTerm.requestFocus()
//        binding.etTerm.post {
//            val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
//            imm?.showSoftInput(binding.etTerm, InputMethodManager.SHOW_IMPLICIT)
//        }

    }
    private fun makeApiRequest1(word: String) {
        isLoading(true)
        viewModel.searchWord(word)
    }

    private fun isLoading(loading: Boolean) {
        binding.loadingIndicator.visibility = if (loading) View.VISIBLE else View.GONE
        binding.btnSearch.isEnabled = !loading
        binding.etTerm.visibility = if (loading) View.GONE else View.VISIBLE
    }

    private fun showErrorDialog(title: String, message: String) {
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
        // Find or create the TermResultViewModel instance
        val viewModel: TermResultViewModel by activityViewModels()
        viewModel.setData(data)

        // Now create the TermResultFragment instance directly
        val resultFragment = TermResultFragment()

        // Begin the transaction and replace the content with the new Fragment
        activity?.supportFragmentManager?.beginTransaction()?.apply {
            replace(android.R.id.content, resultFragment)
            addToBackStack(null)
            commit()
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
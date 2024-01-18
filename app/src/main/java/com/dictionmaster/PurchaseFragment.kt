package com.dictionmaster

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.dictionmaster.databinding.PurchaseFragmentBinding

class PurchaseFragment : Fragment() {

    private lateinit var binding: PurchaseFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.purchase_fragment, container, false
        )
        binding.btnSearch.setOnClickListener{
            activity?.onBackPressed();
        }
        return binding.root
    }
}

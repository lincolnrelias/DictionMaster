package com.dictionmaster.presentation.termresult

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dictionmaster.R
import com.dictionmaster.data.models.DictionaryResponseModel
import com.dictionmaster.databinding.ResultFragmentBinding
import com.dictionmaster.ui.adapters.DefinitionAdapter
import com.dictionmaster.utils.AudioPlayer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TermResultFragment : Fragment(R.layout.result_fragment) {

    private val viewModel: TermResultViewModel by activityViewModels()
    private val audioPlayer = AudioPlayer()

    private var _binding: ResultFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = ResultFragmentBinding.bind(view)

        viewModel.data.observe(viewLifecycleOwner) { data ->
            bindData(data)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.loadingIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.ibSpeaker.visibility = if (isLoading) View.INVISIBLE else View.VISIBLE
        }

        binding.btNewSearch.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun bindData(data: DictionaryResponseModel) {
        with(binding) {
            tvTitle.text = data.word
            tvPhonetic.text = data.getDefaultPhoneticText()
            tvIt.text =  "That's it for \"" + data.word + "\"!"

            val audioUrl = data.getDefaultPhoneticAudio()
            ibSpeaker.isEnabled = audioUrl != null
            ibSpeaker.setOnClickListener {
                audioPlayer.playAudio(audioUrl, viewModel::setLoading)
            }
            binding.rvDefinitions.layoutManager = object : LinearLayoutManager(context) {
                override fun canScrollVertically() = false
            }
            rvDefinitions.adapter = DefinitionAdapter(data.meanings.flatMap { meaning ->
                meaning.definitions.map { definition ->
                    Pair(definition, meaning.partOfSpeech)
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        audioPlayer.release()
        _binding = null
    }
}

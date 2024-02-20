package com.dictionmaster.search

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dictionmaster.R
import com.dictionmaster.databinding.ResultFragmentBinding

class TermResultFragment : Fragment() {
    private lateinit var binding: ResultFragmentBinding
    private var mediaPlayer: MediaPlayer? = null

    companion object {
        const val DATA_KEY = "data_key"
        fun newInstance(data: DictionaryResponseModel): TermResultFragment {
            val fragment = TermResultFragment()
            val bundle = Bundle()
            bundle.putParcelable(DATA_KEY, data)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.result_fragment, container, false
        )
        val data = arguments?.getParcelable<DictionaryResponseModel>(DATA_KEY)
        if (data != null) {
            bindData(data)
        }
        val recyclerView = binding.rvDefinitions
        recyclerView.layoutManager = object : LinearLayoutManager(context) {
            override fun canScrollVertically() = false
        }
        if (data != null) {
            recyclerView.adapter = DefinitionAdapter(data.meanings.flatMap { meaning ->
                meaning.definitions.map { definition ->
                    Pair(definition, meaning.partOfSpeech)
                }
            })
        }

        return binding.root
    }

    private fun bindData(data: DictionaryResponseModel) {
        binding.tvTitle.text = data.word
        binding.tvPhonetic.text = data.getDefaultPhoneticText()
        binding.tvIt.text = "That's it for \"" + data.word + "\"!"
        var audioUrl = data.getDefaultPhoneticAudio()
        if (audioUrl == null) {
            binding.ibSpeaker.isEnabled = false
        } else {
            binding.ibSpeaker.setOnClickListener {
                playAudio(audioUrl)
            }
        }
        binding.btNewSearch.setOnClickListener {
            activity?.onBackPressed()
        }

    }

    private fun playAudio(audioUrl: String) {
        isLoading(true)
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer().apply {
            setDataSource(audioUrl)

            setOnPreparedListener {
                isLoading(false)
                it.start()
            }

            setOnErrorListener { mp, what, extra ->
                isLoading(false)
                true
            }

            setOnCompletionListener {
                it.release()
            }

            prepareAsync()
        }
    }

    private fun isLoading(loading: Boolean) {
        binding.loadingIndicator.visibility = if (loading) View.VISIBLE else View.GONE
        binding.ibSpeaker.visibility = if (loading) View.INVISIBLE else View.VISIBLE
    }

    override fun onDestroy() {
        mediaPlayer?.release()
        super.onDestroy()
    }
}

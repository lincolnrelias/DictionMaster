package com.dictionmaster.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dictionmaster.databinding.DefinitionItemBinding

class DefinitionAdapter(private val definitions: List<Pair<Definition,String>>) :
    RecyclerView.Adapter<DefinitionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DefinitionItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val definition = definitions[position]
        holder.bind(definition.first, position + 1,definition.second)
    }

    override fun getItemCount(): Int = definitions.size

    inner class ViewHolder(private val binding: DefinitionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(definition: Definition, position: Int, partOfSpeech: String) {
            binding.definition = definition
            binding.position = position
            binding.partOfSpeech = partOfSpeech
            binding.executePendingBindings()
        }
    }



}


package com.pmesa48.marvelheroes.ui.characters.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.pmesa48.marvelheroes.databinding.ListItemCharacterBinding
import com.pmesa48.marvelheroes.model.Character

class CharacterAdapter(
    private val onClick: (Character) -> Unit
): ListAdapter<Character, CharacterViewHolder>(HeroDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : CharacterViewHolder {
        val binding = ListItemCharacterBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }

    class HeroDiffCallback : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Character, newItem: Character) =
            oldItem == newItem
    }
}

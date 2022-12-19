package com.pmesa48.marvelheroes.ui.characters.list

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pmesa48.marvelheroes.databinding.ListItemCharacterBinding
import com.pmesa48.marvelheroes.model.Character

class CharacterViewHolder(private val binding: ListItemCharacterBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(character: Character, onClick: (Character) -> Unit) {
        binding.listItemHeroNameTextView.text = character.name
        binding.listItemHeroThumbnailImage.setOnClickListener { onClick.invoke(character) }
        Glide.with(binding.root.context)
            .load(character.thumbnail)
            .centerCrop()
            .into(binding.listItemHeroThumbnailImage)
    }
}

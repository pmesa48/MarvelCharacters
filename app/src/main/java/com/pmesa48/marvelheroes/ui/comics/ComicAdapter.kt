package com.pmesa48.marvelheroes.ui.comics

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.pmesa48.marvelheroes.databinding.ListItemComicBinding
import com.pmesa48.marvelheroes.model.Comic
import com.pmesa48.marvelheroes.ui.common.Utils

class ComicAdapter : ListAdapter<Comic, ComicAdapter.ComicViewHolder>(ComicDiff()) {

    class ComicViewHolder(private val binding: ListItemComicBinding) : ViewHolder(binding.root) {
        fun bind(item: Comic) {
            binding.listItemComicTitle.text = item.name
            Glide.with(itemView)
                .load(item.image)
                .fitCenter()
                .apply(
                    RequestOptions.overrideOf(
                        Utils.dpToPx(200, itemView.context),
                        Utils.dpToPx(200, itemView.context)
                    ))
                .into(binding.listItemComicImage)
        }
    }

    class ComicDiff : DiffUtil.ItemCallback<Comic>() {
        override fun areItemsTheSame(oldItem: Comic, newItem: Comic) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Comic, newItem: Comic) =
            oldItem == newItem

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicViewHolder {
        val binding = ListItemComicBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ComicViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ComicViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

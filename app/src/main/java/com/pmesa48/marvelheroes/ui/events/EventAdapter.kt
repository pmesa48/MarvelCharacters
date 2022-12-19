package com.pmesa48.marvelheroes.ui.events

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.pmesa48.marvelheroes.databinding.ListItemEventBinding
import com.pmesa48.marvelheroes.model.Event
import com.pmesa48.marvelheroes.ui.common.Utils.dpToPx


class EventAdapter : ListAdapter<Event, EventAdapter.EventViewHolder>(EventDiff()) {

    class EventViewHolder(private val binding: ListItemEventBinding) : ViewHolder(binding.root) {
        fun bind(item: Event) {
            binding.titleText.text = item.name
            Glide.with(itemView)
                .load(item.image)
                .fitCenter()
                .apply(
                    RequestOptions.overrideOf(
                        dpToPx(200, itemView.context),
                        dpToPx(200, itemView.context)
                    )
                )
                .into(binding.eventBackgroundImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ListItemEventBinding.inflate(LayoutInflater.from(parent.context))
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class EventDiff : DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(oldItem: Event, newItem: Event) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Event, newItem: Event) =
            oldItem == newItem
    }

}


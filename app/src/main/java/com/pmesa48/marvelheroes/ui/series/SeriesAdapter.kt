package com.pmesa48.marvelheroes.ui.series

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.pmesa48.marvelheroes.databinding.ListItemSeriesBinding
import com.pmesa48.marvelheroes.model.Series
import com.pmesa48.marvelheroes.ui.common.Utils

class SeriesAdapter : ListAdapter<Series, SeriesAdapter.SeriesViewHolder>(SeriesDiff()) {

    class SeriesViewHolder(private val binding: ListItemSeriesBinding) : ViewHolder(binding.root) {
        fun bind(series: Series) {
            binding.titleText.text = series.title
            Glide.with(itemView)
                .load(series.image)
                .fitCenter()
                .apply(
                    RequestOptions.overrideOf(
                        Utils.dpToPx(180, itemView.context),
                        Utils.dpToPx(200, itemView.context)
                    ))
                .into(binding.backgroundImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesViewHolder {
        val binding = ListItemSeriesBinding.inflate(LayoutInflater.from(parent.context))
        return SeriesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SeriesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class SeriesDiff : DiffUtil.ItemCallback<Series>() {
        override fun areItemsTheSame(oldItem: Series, newItem: Series) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Series, newItem: Series) =
            oldItem == newItem
    }

}

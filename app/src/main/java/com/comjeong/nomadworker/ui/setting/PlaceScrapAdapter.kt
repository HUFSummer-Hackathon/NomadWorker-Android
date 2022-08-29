package com.comjeong.nomadworker.ui.setting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.comjeong.nomadworker.databinding.ItemPlaceScrapBinding
import com.comjeong.nomadworker.domain.model.settings.PlaceScrapResult

class PlaceScrapAdapter: ListAdapter<PlaceScrapResult.Result, PlaceScrapAdapter.PlaceScrapViewHolder>(PlaceScrapDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceScrapViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPlaceScrapBinding.inflate(inflater, parent, false)
        return PlaceScrapViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlaceScrapViewHolder, position: Int) {
        holder.bindViews(getItem(position))
    }

    class PlaceScrapViewHolder(private val binding: ItemPlaceScrapBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindViews(scrapItem: PlaceScrapResult.Result) {
            binding.place = scrapItem
            binding.executePendingBindings()
        }
    }

    class PlaceScrapDiffCallback: DiffUtil.ItemCallback<PlaceScrapResult.Result>() {
        override fun areItemsTheSame(
            oldItem: PlaceScrapResult.Result,
            newItem: PlaceScrapResult.Result
        ): Boolean {
            return oldItem.userPlaceId == newItem.userPlaceId
        }

        override fun areContentsTheSame(
            oldItem: PlaceScrapResult.Result,
            newItem: PlaceScrapResult.Result
        ): Boolean {
            return oldItem == newItem
        }
    }
}
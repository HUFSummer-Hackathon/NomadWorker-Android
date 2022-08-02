package com.comjeong.nomadworker.ui.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.comjeong.nomadworker.databinding.ItemNewFeedPlaceSearchBinding
import com.comjeong.nomadworker.domain.model.place.NewFeedPlaceSearchResult

class NewFeedAdapter(private val viewModel : FeedViewModel)
    : ListAdapter<NewFeedPlaceSearchResult.Result, NewFeedAdapter.NewFeedViewHolder>(NewFeedDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewFeedAdapter.NewFeedViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemNewFeedPlaceSearchBinding.inflate(layoutInflater, parent, false)
        return NewFeedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewFeedAdapter.NewFeedViewHolder, position: Int) {
        holder.bindItems(getItem(position))
    }

    inner class NewFeedViewHolder(private val binding : ItemNewFeedPlaceSearchBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItems(placeList : NewFeedPlaceSearchResult.Result) {
            binding.place = placeList
            binding.viewModel = viewModel
            binding.executePendingBindings()
        }
    }
}

class NewFeedDiffCallback() : DiffUtil.ItemCallback<NewFeedPlaceSearchResult.Result>() {
    override fun areItemsTheSame(
        oldItem: NewFeedPlaceSearchResult.Result,
        newItem: NewFeedPlaceSearchResult.Result
    ): Boolean {
        return oldItem.place_name == newItem.place_name
    }

    override fun areContentsTheSame(
        oldItem: NewFeedPlaceSearchResult.Result,
        newItem: NewFeedPlaceSearchResult.Result
    ): Boolean {
        return oldItem == newItem
    }

}
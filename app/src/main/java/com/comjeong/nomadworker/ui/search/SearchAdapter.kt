package com.comjeong.nomadworker.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.comjeong.nomadworker.databinding.ItemSearchPlaceBinding
import com.comjeong.nomadworker.databinding.ItemSearchResultBinding
import com.comjeong.nomadworker.domain.model.place.LocationPlaceResult
import com.comjeong.nomadworker.domain.model.search.PlaceSearchResult
import com.comjeong.nomadworker.ui.place.PlaceRegionViewModel

class SearchAdapter(private val viewModel: SearchViewModel)
    : ListAdapter<PlaceSearchResult.PlaceList, SearchAdapter.SearchViewHolder>(SearchDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemSearchResultBinding.inflate(layoutInflater, parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bindItems(getItem(position))
    }

    inner class SearchViewHolder(private val binding: ItemSearchResultBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItems(place: PlaceSearchResult.PlaceList) {
            binding.viewModel = viewModel
            binding.place = place
            binding.executePendingBindings()
        }
    }

    class SearchDiffCallback() : DiffUtil.ItemCallback<PlaceSearchResult.PlaceList>() {
        override fun areItemsTheSame(
            oldItem: PlaceSearchResult.PlaceList,
            newItem: PlaceSearchResult.PlaceList
        ): Boolean {
            return oldItem.placeId == newItem.placeId
        }

        override fun areContentsTheSame(
            oldItem: PlaceSearchResult.PlaceList,
            newItem: PlaceSearchResult.PlaceList
        ): Boolean {
            return oldItem == newItem
        }
    }
}
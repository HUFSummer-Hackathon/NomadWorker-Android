package com.comjeong.nomadworker.ui.setting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.comjeong.nomadworker.data.datasource.local.NomadSharedPreferences
import com.comjeong.nomadworker.databinding.ItemPlaceScrapBinding
import com.comjeong.nomadworker.domain.model.settings.PlaceScrapListResult

class PlaceScrapAdapter(private val viewModel: SettingsViewModel) :
    ListAdapter<PlaceScrapListResult.Result, PlaceScrapAdapter.PlaceScrapViewHolder>(
        PlaceScrapDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceScrapViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPlaceScrapBinding.inflate(inflater, parent, false)
        return PlaceScrapViewHolder(binding, viewModel)
    }

    override fun onBindViewHolder(holder: PlaceScrapViewHolder, position: Int) {
        holder.bindViews(getItem(position))
    }

    class PlaceScrapViewHolder(
        private val binding: ItemPlaceScrapBinding,
        private val settingsViewModel: SettingsViewModel
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindViews(scrapItem: PlaceScrapListResult.Result) {
            with(binding) {
                place = scrapItem
                viewModel = settingsViewModel

                executePendingBindings()
            }
        }
    }

    class PlaceScrapDiffCallback : DiffUtil.ItemCallback<PlaceScrapListResult.Result>() {
        override fun areItemsTheSame(
            oldItem: PlaceScrapListResult.Result,
            newItem: PlaceScrapListResult.Result
        ): Boolean {
            return oldItem.userPlaceId == newItem.userPlaceId
        }

        override fun areContentsTheSame(
            oldItem: PlaceScrapListResult.Result,
            newItem: PlaceScrapListResult.Result
        ): Boolean {
            return oldItem == newItem
        }
    }
}
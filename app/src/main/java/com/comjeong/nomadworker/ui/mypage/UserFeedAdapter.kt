package com.comjeong.nomadworker.ui.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.comjeong.nomadworker.databinding.ItemMypageUserFeedBinding
import com.comjeong.nomadworker.domain.model.feed.UserTotalFeedsWithInfoResult

class UserFeedAdapter(private val viewModel: MyPageViewModel) : ListAdapter<UserTotalFeedsWithInfoResult.Result.Feed, UserFeedAdapter.UserFeedViewHolder>(
    UserFeedDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserFeedViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemMypageUserFeedBinding.inflate(layoutInflater, parent, false)
        return UserFeedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserFeedViewHolder, position: Int) {
        holder.bindItems(getItem(position))
    }

    inner class UserFeedViewHolder(private val binding: ItemMypageUserFeedBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItems(feed: UserTotalFeedsWithInfoResult.Result.Feed) {
            binding.viewModel = viewModel
            binding.userFeed = feed
            binding.executePendingBindings()
        }
    }

    class UserFeedDiffCallback() : DiffUtil.ItemCallback<UserTotalFeedsWithInfoResult.Result.Feed>() {
        override fun areItemsTheSame(
            oldItem: UserTotalFeedsWithInfoResult.Result.Feed,
            newItem: UserTotalFeedsWithInfoResult.Result.Feed
        ): Boolean {
            return oldItem.feedId == newItem.feedId
        }

        override fun areContentsTheSame(
            oldItem: UserTotalFeedsWithInfoResult.Result.Feed,
            newItem: UserTotalFeedsWithInfoResult.Result.Feed
        ): Boolean {
            return oldItem == newItem
        }
    }
}
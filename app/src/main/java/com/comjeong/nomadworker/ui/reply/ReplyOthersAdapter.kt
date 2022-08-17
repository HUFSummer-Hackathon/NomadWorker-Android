package com.comjeong.nomadworker.ui.reply

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.comjeong.nomadworker.data.datasource.local.NomadSharedPreferences
import com.comjeong.nomadworker.databinding.ItemFeedReplyOthersBinding
import com.comjeong.nomadworker.domain.model.reply.GetReplyResult


/**
 * @author 이재성
 *
 * 기본 Adapter 틀은 만들어 놨고 Others에 해당하는 Model 넣어서 하면 됨
 * 변경해야 할 부분
 * 1. Model
 * 2. ViewHolder
 * 3. DiffCallback
 */

class ReplyOthersAdapter(private val viewModel: ReplyViewModel)
    : ListAdapter<GetReplyResult.Result.Other, ReplyOthersAdapter.ReplyOthersViewHolder>(ReplyOthersDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReplyOthersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFeedReplyOthersBinding.inflate(inflater, parent, false)
        return ReplyOthersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReplyOthersViewHolder, position: Int) {
        holder.bindItems(getItem(position))


    }

    inner class ReplyOthersViewHolder(private val binding: ItemFeedReplyOthersBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItems(reply: GetReplyResult.Result.Other) {
            binding.reply = reply
            binding.viewModel = viewModel
            binding.executePendingBindings()
        }
    }

    class ReplyOthersDiffCallback : DiffUtil.ItemCallback<GetReplyResult.Result.Other>() {
        override fun areItemsTheSame(
            oldItem: GetReplyResult.Result.Other,
            newItem: GetReplyResult.Result.Other
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: GetReplyResult.Result.Other,
            newItem: GetReplyResult.Result.Other
        ): Boolean {
            return oldItem.replyId == newItem.replyId
        }
    }
}
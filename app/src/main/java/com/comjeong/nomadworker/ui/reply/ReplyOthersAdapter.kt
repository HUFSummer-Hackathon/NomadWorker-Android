package com.comjeong.nomadworker.ui.reply

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.comjeong.nomadworker.databinding.ItemFeedReplyOthersBinding


/**
 * @author 이재성
 *
 * 기본 Adapter 틀은 만들어 놨고 Others에 해당하는 Model 넣어서 하면 됨
 * 변경해야 할 부분
 * 1. Model
 * 2. ViewHolder
 * 3. DiffCallback
 */

class ReplyOthersAdapter : ListAdapter<Any, ReplyOthersAdapter.ReplyOthersViewHolder>(ReplyOthersDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReplyOthersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFeedReplyOthersBinding.inflate(inflater, parent, false)
        return ReplyOthersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReplyOthersViewHolder, position: Int) {
        holder.bindItems(getItem(position))
    }

    class ReplyOthersViewHolder(private val binding: ItemFeedReplyOthersBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItems(item: Any?) {
            TODO("Not yet implemented")
        }
    }

    class ReplyOthersDiffCallback : DiffUtil.ItemCallback<Any>() {
        override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
            return true
        }
    }
}
package com.comjeong.nomadworker.ui.reply

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.comjeong.nomadworker.databinding.ItemFeedReplyAuthorBinding
import com.comjeong.nomadworker.domain.model.reply.Author
import timber.log.Timber

/**
 * @author 이재성
 *
 * 기본 Adapter 틀은 만들어 놨고 Author에 해당하는 Model 넣어서 하면 됨
 * 변경해야 할 부분
 * 1. Model
 * 2. ViewHolder
 * 3. DiffCallback
 */

class ReplyAuthorTopAdapter(private val viewModel: ReplyViewModel, private val viewLifecycleOwner: LifecycleOwner)
    : ListAdapter<Author, ReplyAuthorTopAdapter.ReplyAuthorTopViewHolder>(ReplyOthersDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReplyAuthorTopViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFeedReplyAuthorBinding.inflate(inflater, parent, false)
        return ReplyAuthorTopViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReplyAuthorTopViewHolder, position: Int) {
        holder.bindItems(getItem(position))
    }

    inner class ReplyAuthorTopViewHolder(private val binding: ItemFeedReplyAuthorBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindItems(author: Author) {
            binding.author = author
            binding.executePendingBindings()
        }
    }

    class ReplyOthersDiffCallback : DiffUtil.ItemCallback<Author>() {
        override fun areItemsTheSame(
            oldItem: Author,
            newItem: Author
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Author,
            newItem: Author
        ): Boolean {
            return oldItem == newItem
        }
    }
}
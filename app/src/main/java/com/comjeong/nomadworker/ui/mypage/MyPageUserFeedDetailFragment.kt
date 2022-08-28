package com.comjeong.nomadworker.ui.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.comjeong.nomadworker.R
import com.comjeong.nomadworker.common.Constants.FEED_ID_KEY
import com.comjeong.nomadworker.databinding.FragmentMyPageUserFeedDetailBinding
import com.comjeong.nomadworker.ui.common.base.BaseFragment
import com.comjeong.nomadworker.ui.common.util.NavigationUtil.navigate
import com.comjeong.nomadworker.ui.common.util.NavigationUtil.navigateUp
import com.comjeong.nomadworker.ui.reply.ReplyViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class MyPageUserFeedDetailFragment : BaseFragment<FragmentMyPageUserFeedDetailBinding>(R.layout.fragment_my_page_user_feed_detail) {

    private val viewModel: MyPageViewModel by sharedViewModel()
    private val replyViewModel: ReplyViewModel by sharedViewModel()
    private var feedId: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        feedId = requireArguments().getLong(FEED_ID_KEY)

        viewModel.feedId = feedId
        replyViewModel.feedId = feedId

        viewModel.getUserFeedDetail()
        replyViewModel.getReply()

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindView()

        observeReply()
        observeUserFeedDetail()
    }

    private fun observeUserFeedDetail() {
        viewModel.userFeedDetail.observe(viewLifecycleOwner) { feedDetail ->
            binding.feedDetail = feedDetail
        }
    }

    private fun observeReply() {
        replyViewModel.replyList.observe(viewLifecycleOwner) { replyList ->
            Timber.d("댓글 갯수 : ${replyList.size}")
            binding.tvReply.text = replyList.size.toString()
        }
    }

    private fun bindView() {
        binding.tbFeedDetail.setNavigationOnClickListener {
            navigateUp()
        }

        binding.clCommentIconContainer.setOnClickListener {
            moveReplyPage()
        }
    }

    private fun moveReplyPage() {
        navigate(R.id.action_navigation_feed_detail_to_navigation_reply)
    }
}
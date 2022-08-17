package com.comjeong.nomadworker.ui.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.ConcatAdapter
import com.comjeong.nomadworker.R
import com.comjeong.nomadworker.common.Constants.FEED_CONTENT_KEY
import com.comjeong.nomadworker.common.Constants.FEED_ID_KEY
import com.comjeong.nomadworker.common.Constants.PLACE_NAME_KEY
import com.comjeong.nomadworker.common.Constants.USER_IMAGE_KEY
import com.comjeong.nomadworker.common.Constants.USER_NICKNAME_KEY
import com.comjeong.nomadworker.databinding.FragmentMyPageUserFeedDetailBinding
import com.comjeong.nomadworker.domain.model.reply.Author
import com.comjeong.nomadworker.ui.common.BaseFragment
import com.comjeong.nomadworker.ui.common.NavigationUtil.navigateUp
import com.comjeong.nomadworker.ui.common.NavigationUtil.navigateWithBundle
import com.comjeong.nomadworker.ui.reply.ReplyAuthorTopAdapter
import com.comjeong.nomadworker.ui.reply.ReplyOthersAdapter
import com.comjeong.nomadworker.ui.reply.ReplyViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MyPageUserFeedDetailFragment : BaseFragment<FragmentMyPageUserFeedDetailBinding>(R.layout.fragment_my_page_user_feed_detail) {

    private val viewModel: MyPageViewModel by sharedViewModel()
    private val replyViewModel: ReplyViewModel by sharedViewModel()
    private var feedId: Long = 0
    private var placeName: String = ""
    lateinit var replyBundle: Bundle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        feedId = requireArguments().getLong(FEED_ID_KEY)
        placeName = requireArguments().getString(PLACE_NAME_KEY).toString()
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
            replyBundle = bundleOf(
                FEED_CONTENT_KEY to feedDetail.feedContent,
                USER_NICKNAME_KEY to feedDetail.userNickname,
                USER_IMAGE_KEY to feedDetail.userProfileUrl,
                PLACE_NAME_KEY to placeName
            )
        }
    }

    private fun observeReply() {
        replyViewModel.replyList.observe(viewLifecycleOwner) { replyList ->
            binding.tvReply.text = replyList.size.toString()
        }
    }

    private fun bindView() {
        binding.tbFeedDetail.setNavigationOnClickListener {
            navigateUp()
        }

        binding.btnReply.setOnClickListener {
            moveReplyPage()
        }
    }

    private fun moveReplyPage() {
        navigateWithBundle(R.id.action_navigation_feed_detail_to_navigation_reply, replyBundle)
    }
}
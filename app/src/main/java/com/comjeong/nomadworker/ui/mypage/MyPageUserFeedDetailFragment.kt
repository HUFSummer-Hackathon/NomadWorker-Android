package com.comjeong.nomadworker.ui.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.comjeong.nomadworker.R
import com.comjeong.nomadworker.common.Constants.FEED_ID_KEY
import com.comjeong.nomadworker.common.Constants.FEED_USER_ID
import com.comjeong.nomadworker.common.EventObserver
import com.comjeong.nomadworker.data.datasource.local.NomadSharedPreferences
import com.comjeong.nomadworker.data.model.feed.FeedLikeRequestData
import com.comjeong.nomadworker.databinding.FragmentMyPageUserFeedDetailBinding
import com.comjeong.nomadworker.ui.common.base.BaseFragment
import com.comjeong.nomadworker.ui.common.customview.CustomDialog
import com.comjeong.nomadworker.ui.common.util.NavigationUtil.navigate
import com.comjeong.nomadworker.ui.common.util.NavigationUtil.navigateUp
import com.comjeong.nomadworker.ui.feed.FeedViewModel
import com.comjeong.nomadworker.ui.reply.ReplyViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class MyPageUserFeedDetailFragment : BaseFragment<FragmentMyPageUserFeedDetailBinding>(R.layout.fragment_my_page_user_feed_detail) {

    private val viewModel: MyPageViewModel by sharedViewModel()
    private val replyViewModel: ReplyViewModel by sharedViewModel()
    private val feedViewModel: FeedViewModel by sharedViewModel()
    private var feedId: Long = 0
    private var userId: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        feedId = requireArguments().getLong(FEED_ID_KEY)
        userId = requireArguments().getLong(FEED_USER_ID)

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
        observeDeleteFeed()
    }

    private fun observeUserFeedDetail() {
        viewModel.userFeedDetail.observe(viewLifecycleOwner) { feedDetail ->
            binding.feedDetail = feedDetail
            Timber.d("업데이트 된 피드상세 정보 : $feedDetail")
            setFeedOptionVisibility(feedDetail.userNickname.toString())
            setFeedOption()
        }

        viewModel.likesCount.observe(viewLifecycleOwner) { value ->
            binding.likeCount = value
        }

        viewModel.isFavorite.observe(viewLifecycleOwner) { value ->
            binding.isFavorite = value
        }
    }

    private fun observeReply() {
        replyViewModel.replyList.observe(viewLifecycleOwner) { replyList ->
            Timber.d("댓글 갯수 : ${replyList.size}")
            binding.tvReply.text = replyList.size.toString()
        }
    }

    private fun observeDeleteFeed() {
        viewModel.isSuccessDelete.observe(viewLifecycleOwner, EventObserver<Boolean> { isSuccessDelete ->
            if(isSuccessDelete) {
                navigateUp()
            }
            else{
                Toast.makeText(requireActivity(), "오류입니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setConfirmDeleteFeed() {
        val dialog = CustomDialog(requireActivity())
        dialog.showDialog("피드를 삭제하시겠습니까?", "확인을 누르면 피드가 삭제됩니다.",true)
        dialog.setDialogButtonClickListener(object :
            CustomDialog.DialogButtonClickListener {
            override fun onClicked(event: CustomDialog.DialogEvent) {
                when(event) {
                    CustomDialog.DialogEvent.POSITIVE -> {
                        viewModel.deleteFeed()
                    }
                    else -> Unit
                }
            }
        })
    }
    private fun setFeedOptionVisibility(userNickname: String) {
        Timber.d("닉네임 비교 : ${viewModel.userId} $userNickname ${NomadSharedPreferences.getUserNickName()}")
        if(userId <= 0) {
                binding.tbFeedDetail.menu.clear()
        }
    }

    private fun setFeedOption() {
        binding.tbFeedDetail.setOnMenuItemClickListener(object : Toolbar.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                if (item != null) {
                    when(item.itemId) {
                        R.id.navigation_delete -> {
                            setConfirmDeleteFeed()
                            return true
                        }
                        else -> Unit
                    }
                }
                return true
            }
        })
    }

    private fun bindView() {
        binding.tbFeedDetail.setNavigationOnClickListener {
            navigateUp()
        }

        binding.clCommentIconContainer.setOnClickListener {
            moveReplyPage()
        }

        binding.ivLike.setOnClickListener {
            viewModel.postFeedLike(FeedLikeRequestData(feedId))
        }
    }

    private fun moveReplyPage() {
        navigate(R.id.action_navigation_feed_detail_to_navigation_reply)
    }
}
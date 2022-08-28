package com.comjeong.nomadworker.ui.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.comjeong.nomadworker.R
import com.comjeong.nomadworker.common.Constants.FEED_ID_KEY
import com.comjeong.nomadworker.common.EventObserver
import com.comjeong.nomadworker.data.datasource.local.NomadSharedPreferences
import com.comjeong.nomadworker.databinding.FragmentMyPageBinding
import com.comjeong.nomadworker.ui.common.base.BaseFragment
import com.comjeong.nomadworker.ui.common.util.NavigationUtil.navigate
import com.comjeong.nomadworker.ui.common.util.NavigationUtil.navigateWithBundle
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MyPageFragment : BaseFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {

    private val viewModel: MyPageViewModel by sharedViewModel()
    private var userId: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        userId = NomadSharedPreferences.getUserId()
        NomadSharedPreferences.loadUserInfo()
        viewModel.userId = userId

        viewModel.getUserTotalFeedsWithInfo()

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUserFeed()
        observeUserInfo()
        observeFeedDetailEvent()

        binding.btnMypageSetting.setOnClickListener {
            navigate(R.id.action_my_page_to_settings)
        }
    }

    private fun observeUserInfo() {
        viewModel.userInfo.observe(viewLifecycleOwner) { userInfo ->
            binding.userInfo = userInfo
        }
    }

    private fun observeFeedDetailEvent() {
        viewModel.openFeedDetailEvent.observe(viewLifecycleOwner, EventObserver<Long> { feedId ->
            moveFeedDetail(feedId)
        })
    }

    private fun moveFeedDetail(feedId: Long) {
        navigateWithBundle(
            R.id.action_my_page_to_feed_detail, bundleOf(
                FEED_ID_KEY to feedId
            )
        )
    }

    private fun setUserFeed() {
        binding.rvUserFeed.adapter = UserFeedAdapter(viewModel).apply {
            viewModel.userFeedList.observe(viewLifecycleOwner) { feedList ->
                submitList(feedList)
            }
        }
    }

}
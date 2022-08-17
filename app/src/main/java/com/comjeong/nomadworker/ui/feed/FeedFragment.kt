package com.comjeong.nomadworker.ui.feed

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.comjeong.nomadworker.R
import com.comjeong.nomadworker.common.Constants
import com.comjeong.nomadworker.common.EventObserver
import com.comjeong.nomadworker.data.model.feed.FeedLikeRequestData
import com.comjeong.nomadworker.databinding.FragmentFeedBinding
import com.comjeong.nomadworker.ui.common.BaseFragment
import com.comjeong.nomadworker.ui.common.NavigationUtil.navigate
import com.comjeong.nomadworker.ui.common.NavigationUtil.navigateWithBundle
import org.koin.androidx.viewmodel.ext.android.viewModel

class FeedFragment : BaseFragment<FragmentFeedBinding>(R.layout.fragment_feed) {

    private val viewModel: FeedViewModel by viewModel()

    val feedLike = FeedLike()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindCreateFeedPage()

        setFeedAdapter()
        setFeedList()

        observeFeedDetailEvent()
    }

    private fun setFeedAdapter() {
        binding.rvFeedList.adapter = FeedAdapter(viewModel, feedLike).apply {
            viewModel.feedList.observe(viewLifecycleOwner) { feedList ->
                submitList(feedList)
            }
        }
    }

    private fun setFeedList() {
        viewModel.getTotalFeeds()
    }

    private fun bindCreateFeedPage(){
        binding.btnNewFeed.setOnClickListener {
            navigate(R.id.action_navigation_feed_to_newFeedActivity)
        }
    }

    inner class FeedLike() {
        fun getFeedId(feedId: Long) {
            viewModel.postFeedLike(FeedLikeRequestData(feedId))
        }
    }

    private fun observeFeedDetailEvent() {
        viewModel.openFeedDetailEvent.observe(viewLifecycleOwner, EventObserver<Long> { feedId ->
            moveFeedDetail(feedId, viewModel.placeName)
        })
    }

    private fun moveFeedDetail(feedId: Long, placeName: String) {
        navigateWithBundle(
            R.id.action_navigation_feed_to_navigation_feed_detail, bundleOf(
                Constants.FEED_ID_KEY to feedId,
                Constants.PLACE_NAME_KEY to placeName
            )
        )
    }

}
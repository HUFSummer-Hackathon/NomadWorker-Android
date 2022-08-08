package com.comjeong.nomadworker.ui.feed

import android.os.Bundle
import android.view.View
import com.comjeong.nomadworker.R
import com.comjeong.nomadworker.data.model.feed.FeedLikeRequestData
import com.comjeong.nomadworker.databinding.FragmentFeedBinding
import com.comjeong.nomadworker.ui.common.BaseFragment
import com.comjeong.nomadworker.ui.common.NavigationUtil.navigate
import org.koin.androidx.viewmodel.ext.android.viewModel

class FeedFragment : BaseFragment<FragmentFeedBinding>(R.layout.fragment_feed) {

    private val viewModel: FeedViewModel by viewModel()

    val feedLike = FeedLike()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindCreateFeedPage()

        setFeedAdapter()
        setFeedList()

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

}
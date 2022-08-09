package com.comjeong.nomadworker.ui.reply

import android.os.Bundle
import android.view.View
import com.comjeong.nomadworker.R
import com.comjeong.nomadworker.databinding.FragmentFeedReplyBinding
import com.comjeong.nomadworker.ui.common.BaseFragment

/**
 * @author 이재성
 *
 * Adapter 두개 [ReplyAuthorTopAdapter], [ReplyOthersAdapter]를 ConcatAdapter로 연결해서 만들면 됨
 */

class ReplyFragment : BaseFragment<FragmentFeedReplyBinding>(R.layout.fragment_feed_reply) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}
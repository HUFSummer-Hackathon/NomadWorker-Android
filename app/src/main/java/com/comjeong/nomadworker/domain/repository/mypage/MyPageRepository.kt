package com.comjeong.nomadworker.domain.repository.mypage

import com.comjeong.nomadworker.domain.model.feed.UserTotalFeedsWithInfoResult
import com.comjeong.nomadworker.domain.model.mypage.DeleteFeedResult
import com.comjeong.nomadworker.domain.model.mypage.UserFeedDetailResult

interface MyPageRepository {

    suspend fun getUserTotalFeedsWithInfo(userId: Long): UserTotalFeedsWithInfoResult

    suspend fun getUserFeedDetail(feedId: Long): UserFeedDetailResult

    suspend fun deleteFeed(feedId: Long): DeleteFeedResult
}
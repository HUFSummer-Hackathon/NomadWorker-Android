package com.comjeong.nomadworker.data.datasource.source.mypage

import com.comjeong.nomadworker.data.model.mypage.UserFeedDetailResponseData
import com.comjeong.nomadworker.data.model.mypage.UserTotalFeedsWithInfoResponseData

interface MyPageDataSource {

    suspend fun getUserTotalFeedsWithInfo(userId: Long): UserTotalFeedsWithInfoResponseData

    suspend fun getUserFeedDetail(feedId: Long): UserFeedDetailResponseData
}
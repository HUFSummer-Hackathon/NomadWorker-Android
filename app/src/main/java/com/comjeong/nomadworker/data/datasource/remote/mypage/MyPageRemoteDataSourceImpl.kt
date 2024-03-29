package com.comjeong.nomadworker.data.datasource.remote.mypage

import com.comjeong.nomadworker.data.datasource.source.mypage.MyPageDataSource
import com.comjeong.nomadworker.data.model.mypage.DeleteFeedResponseData
import com.comjeong.nomadworker.data.model.mypage.UserFeedDetailResponseData
import com.comjeong.nomadworker.data.model.mypage.UserTotalFeedsWithInfoResponseData
import com.comjeong.nomadworker.data.network.api.AuthApi
import com.comjeong.nomadworker.data.network.api.FeedApi

class MyPageRemoteDataSourceImpl(private val authApi: AuthApi, private val feedApi: FeedApi) : MyPageDataSource {

    override suspend fun getUserTotalFeedsWithInfo(userId: Long): UserTotalFeedsWithInfoResponseData {
        return feedApi.getUserTotalFeedsWithUserInfo(userId)
    }

    override suspend fun getUserFeedDetail(feedId: Long): UserFeedDetailResponseData {
        return feedApi.getUserFeedDetail(feedId)
    }

    override suspend fun deleteFeed(feedId: Long): DeleteFeedResponseData {
        return feedApi.deleteFeed(feedId)
    }
}
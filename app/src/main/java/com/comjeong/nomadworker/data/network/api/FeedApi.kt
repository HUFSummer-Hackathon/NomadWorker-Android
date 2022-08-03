package com.comjeong.nomadworker.data.network.api

import com.comjeong.nomadworker.data.model.feed.TotalFeedsResponseData
import com.comjeong.nomadworker.data.model.mypage.UserFeedDetailResponseData
import com.comjeong.nomadworker.data.model.mypage.UserTotalFeedsWithInfoResponseData
import retrofit2.http.GET
import retrofit2.http.Query

interface FeedApi {

    @GET("feeds/total")
    suspend fun getTotalFeeds(): TotalFeedsResponseData

    @GET("feeds/usertotal")
    suspend fun getUserTotalFeedsWithUserInfo(
        @Query("u_id") userId: Long
    ): UserTotalFeedsWithInfoResponseData

    @GET("feeds/one")
    suspend fun getUserFeedDetail(
        @Query("f_id") feedId: Long
    ): UserFeedDetailResponseData
}
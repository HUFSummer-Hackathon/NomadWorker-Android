package com.comjeong.nomadworker.data.network.api

import com.comjeong.nomadworker.data.model.feed.*
import com.comjeong.nomadworker.data.model.mypage.UserFeedDetailResponseData
import com.comjeong.nomadworker.data.model.mypage.UserTotalFeedsWithInfoResponseData
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

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

    @GET("search/placetag")
    suspend fun getNewFeedPlaceSearchResult(
        @Query("placeName") placeName : String
    ) : NewFeedPlaceSearchResultResponseData

    @Multipart
    @POST("feeds/new")
    suspend fun postNewFeed(
        @Part file : MultipartBody.Part,
        @Part("feed_content") content : RequestBody,
        @Part("p_id") placeId : RequestBody
    ) : PostNewFeedResponseData

    @POST("feeds/likes")
    suspend fun postFeedLike(
        @Body body: FeedLikeRequestData
    ): FeedLikeResponseData
}
package com.comjeong.nomadworker.domain.repository.feed

import com.comjeong.nomadworker.data.model.feed.FeedLikeRequestData
import com.comjeong.nomadworker.data.model.feed.FeedLikeResponseData
import com.comjeong.nomadworker.domain.model.feed.FeedLikeResult
import com.comjeong.nomadworker.domain.model.feed.PostNewFeedResult
import com.comjeong.nomadworker.domain.model.feed.TotalFeedsResult
import com.comjeong.nomadworker.domain.model.place.NewFeedPlaceSearchResult
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface FeedRepository {

    suspend fun getTotalFeeds() : TotalFeedsResult

    suspend fun getNewFeedPlaceSearchResult(placeName: String) : NewFeedPlaceSearchResult

    suspend fun postNewFeed(placeImage: MultipartBody.Part, content: RequestBody, placeId: RequestBody) : PostNewFeedResult

    suspend fun postFeedLike(body: FeedLikeRequestData): FeedLikeResult
}
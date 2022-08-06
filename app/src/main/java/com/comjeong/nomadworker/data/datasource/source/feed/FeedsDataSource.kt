package com.comjeong.nomadworker.data.datasource.source.feed

import com.comjeong.nomadworker.data.model.feed.NewFeedPlaceSearchResultResponseData
import com.comjeong.nomadworker.data.model.feed.PostNewFeedResponseData
import com.comjeong.nomadworker.data.model.feed.TotalFeedsResponseData
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface FeedsDataSource {

    suspend fun getTotalFeeds(): TotalFeedsResponseData

    suspend fun getNewFeedPlaceSearchResult(placeName : String): NewFeedPlaceSearchResultResponseData

    suspend fun postNewFeed(placeImage : MultipartBody.Part, content: RequestBody, placeId: RequestBody): PostNewFeedResponseData

}
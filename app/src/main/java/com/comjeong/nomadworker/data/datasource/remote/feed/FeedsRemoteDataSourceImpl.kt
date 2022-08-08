package com.comjeong.nomadworker.data.datasource.remote.feed

import com.comjeong.nomadworker.data.datasource.source.feed.FeedsDataSource
import com.comjeong.nomadworker.data.model.feed.*
import com.comjeong.nomadworker.data.network.api.FeedApi
import okhttp3.MultipartBody
import okhttp3.RequestBody

class FeedsRemoteDataSourceImpl(private val api: FeedApi) : FeedsDataSource {

    override suspend fun getTotalFeeds(): TotalFeedsResponseData {
        return api.getTotalFeeds()
    }

    override suspend fun getNewFeedPlaceSearchResult(placeName : String): NewFeedPlaceSearchResultResponseData {
        return api.getNewFeedPlaceSearchResult(placeName)
    }

    override suspend fun postNewFeed(placeImage: MultipartBody.Part, content: RequestBody, placeId: RequestBody): PostNewFeedResponseData {
        return api.postNewFeed(placeImage, content, placeId)
    }

    override suspend fun postFeedLike(body: FeedLikeRequestData): FeedLikeResponseData {
        return api.postFeedLike(body)
    }
}
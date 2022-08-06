package com.comjeong.nomadworker.data.repository.feed

import com.comjeong.nomadworker.data.datasource.source.feed.FeedsDataSource
import com.comjeong.nomadworker.data.mapper.FeedMapper
import com.comjeong.nomadworker.domain.model.feed.PostNewFeedResult
import com.comjeong.nomadworker.domain.model.feed.TotalFeedsResult
import com.comjeong.nomadworker.domain.model.place.NewFeedPlaceSearchResult
import com.comjeong.nomadworker.domain.repository.feed.FeedRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class FeedRepositoryImpl(private val dataSource: FeedsDataSource) : FeedRepository {
    override suspend fun getTotalFeeds(): TotalFeedsResult {
        return FeedMapper.mapToTotalFeedsResult(dataSource.getTotalFeeds())
    }

    override suspend fun getNewFeedPlaceSearchResult(placeName: String): NewFeedPlaceSearchResult {
        return FeedMapper.mapToNewFeedPlaceSearchResult(dataSource.getNewFeedPlaceSearchResult(placeName))
    }

    override suspend fun postNewFeed(placeImage: MultipartBody.Part, content: RequestBody, placeId: RequestBody)
    : PostNewFeedResult {
        return FeedMapper.mapToPostNewFeedResult(dataSource.postNewFeed(placeImage, content, placeId))
    }
}
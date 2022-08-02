package com.comjeong.nomadworker.domain.repository.feed

import com.comjeong.nomadworker.domain.model.feed.TotalFeedsResult
import com.comjeong.nomadworker.domain.model.place.NewFeedPlaceSearchResult

interface FeedRepository {

    suspend fun getTotalFeeds() : TotalFeedsResult

    suspend fun getNewFeedPlaceSearchResult(placeName: String) : NewFeedPlaceSearchResult
}
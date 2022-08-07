package com.comjeong.nomadworker.data.mapper

import com.comjeong.nomadworker.data.model.feed.*
import com.comjeong.nomadworker.domain.model.feed.FeedLikeResult
import com.comjeong.nomadworker.domain.model.feed.PostNewFeedResult
import com.comjeong.nomadworker.domain.model.feed.TotalFeedsResult
import com.comjeong.nomadworker.domain.model.place.NewFeedPlaceSearchResult

object FeedMapper {

    fun mapToTotalFeedsResult(body: TotalFeedsResponseData): TotalFeedsResult {
        return TotalFeedsResult(
            message = body.message,
            status = body.status,
            data = body.data?.map { feed ->
                TotalFeedsResult.Result(
                    userName = feed.userName,
                    userProfile = feed.userProfile,
                    placeId = feed.placeId,
                    feedId = feed.feedId,
                    feedImageUrl = feed.feedImageUrl,
                    feedContent = feed.feedContent,
                    feedLike = feed.feedLike,
                    placeName = feed.placeName
                )
            }
        )
    }

    fun mapToNewFeedPlaceSearchResult(body: NewFeedPlaceSearchResultResponseData): NewFeedPlaceSearchResult {
        return NewFeedPlaceSearchResult(
            message = body.message,
            status = body.status,
            placeList = body.placeList?.map { place ->
                NewFeedPlaceSearchResult.Result(
                    placeId = place.placeId,
                    placeName = place.placeName
                )
            }
        )
    }

    fun mapToPostNewFeedResult(body: PostNewFeedResponseData) : PostNewFeedResult {
        return PostNewFeedResult(
            message = body.message,
            status = body.status
        )
    }

    fun mapToFeedLikeResult(body: FeedLikeResponseData): FeedLikeResult {
        return FeedLikeResult(
            message = body.message,
            status = body.status,
            data = FeedLikeResult.Result(
                likeStatus = body.data?.likeStatus,
                likeCount = body.data?.likeCount
            )
        )
    }
}
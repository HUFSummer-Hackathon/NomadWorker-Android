package com.comjeong.nomadworker.data.mapper

import com.comjeong.nomadworker.data.model.mypage.DeleteFeedResponseData
import com.comjeong.nomadworker.data.model.mypage.ProfileImageResponseData
import com.comjeong.nomadworker.data.model.mypage.UserFeedDetailResponseData
import com.comjeong.nomadworker.data.model.mypage.UserTotalFeedsWithInfoResponseData
import com.comjeong.nomadworker.domain.model.feed.UserTotalFeedsWithInfoResult
import com.comjeong.nomadworker.domain.model.mypage.DeleteFeedResult
import com.comjeong.nomadworker.domain.model.mypage.ProfileImageResult
import com.comjeong.nomadworker.domain.model.mypage.UserFeedDetailResult

object MyPageMapper {

    fun mapToUserTotalFeedResult(body: UserTotalFeedsWithInfoResponseData): UserTotalFeedsWithInfoResult {
        return UserTotalFeedsWithInfoResult(
            message = body.message,
            status = body.status,
            data = UserTotalFeedsWithInfoResult.Result(
                userNickname = body.data?.userNickname,
                userProfileImage = body.data?.userImageUrl,
                feedList = body.data?.feedList?.map { feed ->
                    UserTotalFeedsWithInfoResult.Result.Feed(
                        feedImageUrl = feed.feedImageUrl,
                        feedId = feed.feedId
                    )
                }
            )
        )
    }

    fun mapToUserFeedDetailResult(body: UserFeedDetailResponseData): UserFeedDetailResult {
        return UserFeedDetailResult(
            message = body.message,
            status = body.status,
            data = UserFeedDetailResult.Result(
                feedImage = body.data?.feedImage,
                feedContent = body.data?.feedContent,
                feedLike = body.data?.feedLike,
                userProfileUrl = body.data?.userProfileUrl,
                userNickname = body.data?.userNickname
            )
        )
    }

    fun mapToProfileImageResult(body: ProfileImageResponseData): ProfileImageResult {
        return ProfileImageResult(
            message = body.message,
            status = body.status
        )
    }

    fun mapToDeleteFeedResult(body: DeleteFeedResponseData): DeleteFeedResult {
        return DeleteFeedResult(
            message = body.message,
            status = body.status
        )
    }
}
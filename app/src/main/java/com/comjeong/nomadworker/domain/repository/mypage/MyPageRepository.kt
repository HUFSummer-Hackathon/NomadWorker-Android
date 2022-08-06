package com.comjeong.nomadworker.domain.repository.mypage

import com.comjeong.nomadworker.domain.model.feed.UserTotalFeedsWithInfoResult
import com.comjeong.nomadworker.domain.model.mypage.ProfileImageResult
import com.comjeong.nomadworker.domain.model.mypage.UserFeedDetailResult
import okhttp3.MultipartBody

interface MyPageRepository {

    suspend fun getUserTotalFeedsWithInfo(userId: Long): UserTotalFeedsWithInfoResult

    suspend fun getUserFeedDetail(feedId: Long): UserFeedDetailResult

    suspend fun updateUserProfileImage(profileImage: MultipartBody.Part): ProfileImageResult
}
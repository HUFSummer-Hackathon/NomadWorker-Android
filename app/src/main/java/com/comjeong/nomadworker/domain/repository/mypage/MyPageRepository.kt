package com.comjeong.nomadworker.domain.repository.mypage

import com.comjeong.nomadworker.domain.model.feed.UserTotalFeedResult
import com.comjeong.nomadworker.domain.model.mypage.ProfileImageResult
import com.comjeong.nomadworker.domain.model.mypage.UserFeedDetailResult
import com.comjeong.nomadworker.domain.model.mypage.UserInfoResult
import okhttp3.MultipartBody

interface MyPageRepository {

    suspend fun getUserInfo(): UserInfoResult

    suspend fun getUserTotalFeed(): UserTotalFeedResult

    suspend fun getUserFeedDetail(feedId: Long): UserFeedDetailResult

    suspend fun updateUserProfileImage(profileImage: MultipartBody.Part): ProfileImageResult
}
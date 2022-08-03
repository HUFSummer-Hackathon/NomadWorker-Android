package com.comjeong.nomadworker.data.datasource.source.mypage

import com.comjeong.nomadworker.data.model.mypage.ProfileImageResponseData
import com.comjeong.nomadworker.data.model.mypage.UserFeedDetailResponseData
import com.comjeong.nomadworker.data.model.mypage.UserInfoResponseData
import com.comjeong.nomadworker.data.model.mypage.UserTotalFeedsResponseData
import okhttp3.MultipartBody

interface MyPageDataSource {

    suspend fun getUserInfo(): UserInfoResponseData

    suspend fun getUserTotalFeed(): UserTotalFeedsResponseData

    suspend fun getUserFeedDetail(feedId: Long): UserFeedDetailResponseData

    suspend fun uploadUserProfileImage(profileImage: MultipartBody.Part): ProfileImageResponseData
}
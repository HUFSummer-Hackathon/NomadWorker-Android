package com.comjeong.nomadworker.data.datasource.source.mypage

import com.comjeong.nomadworker.data.model.mypage.response.ProfileImageResponseData
import com.comjeong.nomadworker.data.model.mypage.UserFeedDetailResponseData
import com.comjeong.nomadworker.data.model.mypage.response.UserTotalFeedsWithInfoResponseData
import okhttp3.MultipartBody

interface MyPageDataSource {

    suspend fun getUserTotalFeedsWithInfo(userId: Long): UserTotalFeedsWithInfoResponseData

    suspend fun getUserFeedDetail(feedId: Long): UserFeedDetailResponseData

    suspend fun uploadUserProfileImage(profileImage: MultipartBody.Part): ProfileImageResponseData
}
package com.comjeong.nomadworker.data.datasource.source.settings

import com.comjeong.nomadworker.data.model.mypage.ProfileImageResponseData
import com.comjeong.nomadworker.data.model.settings.PlaceScrapListResponseData
import okhttp3.MultipartBody

interface SettingsRemoteDataSource {

    suspend fun uploadUserProfileImage(profileImage: MultipartBody.Part): ProfileImageResponseData

    suspend fun getPlaceScrapListByUserId(userId: Long): PlaceScrapListResponseData
}
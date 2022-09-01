package com.comjeong.nomadworker.data.datasource.remote.settings

import com.comjeong.nomadworker.data.datasource.source.settings.SettingsRemoteDataSource
import com.comjeong.nomadworker.data.model.mypage.ProfileImageResponseData
import com.comjeong.nomadworker.data.model.settings.PlaceScrapListResponseData
import com.comjeong.nomadworker.data.network.api.AuthApi
import okhttp3.MultipartBody

class SettingsRemoteDataSourceImpl(private val authApi: AuthApi) : SettingsRemoteDataSource {

    override suspend fun uploadUserProfileImage(profileImage: MultipartBody.Part): ProfileImageResponseData {
        return authApi.updateUserProfileImage(profileImage)
    }

    override suspend fun getPlaceScrapListByUserId(userId: Long): PlaceScrapListResponseData {
        return authApi.getPlaceScrapListByUserId(userId)
    }
}
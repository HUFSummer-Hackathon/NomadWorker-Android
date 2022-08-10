package com.comjeong.nomadworker.data.datasource.source.settings

import com.comjeong.nomadworker.data.model.mypage.ProfileImageResponseData
import okhttp3.MultipartBody

interface SettingsRemoteDataSource {

    suspend fun uploadUserProfileImage(profileImage: MultipartBody.Part): ProfileImageResponseData
}
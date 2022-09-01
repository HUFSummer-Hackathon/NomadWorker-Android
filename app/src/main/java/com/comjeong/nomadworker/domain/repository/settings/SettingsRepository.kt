package com.comjeong.nomadworker.domain.repository.settings

import com.comjeong.nomadworker.domain.model.mypage.ProfileImageResult
import com.comjeong.nomadworker.domain.model.settings.PlaceScrapListResult
import okhttp3.MultipartBody

interface SettingsRepository {

    suspend fun updateUserProfileImage(profileImage: MultipartBody.Part): ProfileImageResult

    suspend fun getPlaceScrapListByUserId(userId: Long): PlaceScrapListResult
}
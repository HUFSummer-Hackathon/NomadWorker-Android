package com.comjeong.nomadworker.domain.repository.settings

import com.comjeong.nomadworker.domain.model.mypage.ProfileImageResult
import okhttp3.MultipartBody

interface SettingsRepository {

    suspend fun updateUserProfileImage(profileImage: MultipartBody.Part): ProfileImageResult
}
package com.comjeong.nomadworker.data.repository.settings

import com.comjeong.nomadworker.data.datasource.source.settings.SettingsRemoteDataSource
import com.comjeong.nomadworker.data.mapper.MyPageMapper
import com.comjeong.nomadworker.domain.model.mypage.ProfileImageResult
import com.comjeong.nomadworker.domain.repository.settings.SettingsRepository
import okhttp3.MultipartBody

class SettingsRepositoryImpl(private val dataSource: SettingsRemoteDataSource) : SettingsRepository {

    override suspend fun updateUserProfileImage(profileImage: MultipartBody.Part): ProfileImageResult {
        return MyPageMapper.mapToProfileImageResult(dataSource.uploadUserProfileImage(profileImage))
    }

}
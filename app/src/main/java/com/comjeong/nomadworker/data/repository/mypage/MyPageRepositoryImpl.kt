package com.comjeong.nomadworker.data.repository.mypage

import com.comjeong.nomadworker.data.datasource.source.mypage.MyPageDataSource
import com.comjeong.nomadworker.data.mapper.MyPageMapper
import com.comjeong.nomadworker.domain.model.feed.UserTotalFeedsWithInfoResult
import com.comjeong.nomadworker.domain.model.mypage.ProfileImageResult
import com.comjeong.nomadworker.domain.model.mypage.UserFeedDetailResult
import com.comjeong.nomadworker.domain.repository.mypage.MyPageRepository
import okhttp3.MultipartBody

class MyPageRepositoryImpl(private val dataSource: MyPageDataSource) : MyPageRepository {

    override suspend fun getUserTotalFeedsWithInfo(userId: Long): UserTotalFeedsWithInfoResult {
        return MyPageMapper.mapToUserTotalFeedResult(dataSource.getUserTotalFeedsWithInfo(userId))
    }

    override suspend fun getUserFeedDetail(feedId: Long): UserFeedDetailResult {
        return MyPageMapper.mapToUserFeedDetailResult(dataSource.getUserFeedDetail(feedId))
    }

    override suspend fun updateUserProfileImage(profileImage: MultipartBody.Part): ProfileImageResult {
        return MyPageMapper.mapToProfileImageResult(dataSource.uploadUserProfileImage(profileImage))
    }
}
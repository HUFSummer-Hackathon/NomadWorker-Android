package com.comjeong.nomadworker.data.mapper

import com.comjeong.nomadworker.data.model.signup.SignUpResponseData
import com.comjeong.nomadworker.data.model.signup.UserEmailResponseData
import com.comjeong.nomadworker.data.model.signup.UserNicknameResponseData
import com.comjeong.nomadworker.domain.model.signup.SignUpResult
import com.comjeong.nomadworker.domain.model.signup.UserEmailResult
import com.comjeong.nomadworker.domain.model.signup.UserNicknameResult

object SignUpMapper {

    fun mapToSignUpResult(responseData: SignUpResponseData): SignUpResult {
        return SignUpResult(
            message = responseData.message,
            status = responseData.status,
            data = SignUpResult.MemberInfo(
                userId = responseData.data.userId,
                userProfileImageUrl = responseData.data.userProfileImageUrl,
                userNickname = responseData.data.userNickname,
                accessToken = responseData.data.accessToken,
                latitude = responseData.data.latitude,
                longitude = responseData.data.longitude
            )
        )
    }

    fun mapToUserEmailResult(responseData: UserEmailResponseData): UserEmailResult {
        return UserEmailResult(
            message = responseData.message,
            status = responseData.status,
            data = UserEmailResult.Result(
                verificationCode = responseData.data.verificationCode
            )
        )
    }

    fun mapToUserNicknameResult(responseData: UserNicknameResponseData): UserNicknameResult {
        return UserNicknameResult(
            message = responseData.message,
            status = responseData.status
        )
    }
}
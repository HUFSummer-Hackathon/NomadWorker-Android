package com.comjeong.nomadworker.domain.model.signup

data class SignUpResult(
    val message: String,
    val status: Int,
    val data: MemberInfo
) {
    data class MemberInfo(
        val userId: Long,
        val userProfileImageUrl: String,
        val userNickname: String,
        val accessToken: String,
        val latitude: Float,
        val longitude: Float
    )
}

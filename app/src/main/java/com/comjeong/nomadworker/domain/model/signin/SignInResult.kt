package com.comjeong.nomadworker.domain.model.signin

data class SignInResult(
    val message: String,
    val status: Int,
    val data: Result
) {
    data class Result(
        val nickname: String?,
        val accessToken: String?,
        val latitude: Float,
        val longitude: Float
    )
}

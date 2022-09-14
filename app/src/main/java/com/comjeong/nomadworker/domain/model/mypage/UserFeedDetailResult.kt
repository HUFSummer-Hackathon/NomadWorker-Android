package com.comjeong.nomadworker.domain.model.mypage

data class UserFeedDetailResult(
    val message: String,
    val status: Int,
    val data: Result?
) {
    data class Result(
        val feedImage: String?,
        val feedContent: String?,
        val likeCount: Int?,
        val likeStatus: Int?,
        val userProfileUrl: String?,
        val userNickname: String?
    )
}

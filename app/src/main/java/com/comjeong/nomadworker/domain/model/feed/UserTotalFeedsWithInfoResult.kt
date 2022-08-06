package com.comjeong.nomadworker.domain.model.feed

data class UserTotalFeedsWithInfoResult(
    val message: String,
    val status: Int,
    val data: Result?
) {
    data class Result(
        val userNickname: String?,
        val userProfileImage: String?,
        val feedList: List<Feed>?
    ) {
        data class Feed(
            val feedImageUrl: String?,
            val feedId: Long?
        )
    }
}
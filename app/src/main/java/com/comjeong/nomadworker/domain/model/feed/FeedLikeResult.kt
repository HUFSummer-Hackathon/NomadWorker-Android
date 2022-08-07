package com.comjeong.nomadworker.domain.model.feed

data class FeedLikeResult(
    val message: String,
    val status: Int,
    val data: Result?
) {
    data class Result(
        val likeStatus: Boolean?,
        val likeCount: Int?
    )
}

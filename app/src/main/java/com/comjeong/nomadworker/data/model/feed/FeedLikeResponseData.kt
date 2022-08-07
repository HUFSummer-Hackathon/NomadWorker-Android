package com.comjeong.nomadworker.data.model.feed

import com.google.gson.annotations.SerializedName

data class FeedLikeResponseData(
    @SerializedName("message") val message: String,
    @SerializedName("status") val status: Int,
    @SerializedName("data") val data: Result?
) {
    data class Result(
        @SerializedName("like_status") val likeStatus: Boolean,
        @SerializedName("like_count") val likeCount: Int
    )
}

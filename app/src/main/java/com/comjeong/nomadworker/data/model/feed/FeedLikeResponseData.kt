package com.comjeong.nomadworker.data.model.feed

import com.google.gson.annotations.SerializedName

data class FeedLikeResponseData(
    @SerializedName("message") val message: String,
    @SerializedName("status") val status: Int
)

package com.comjeong.nomadworker.data.model.feed

import com.google.gson.annotations.SerializedName

data class FeedLikeRequestData(
    @SerializedName("f_id") val feedId: Long
)

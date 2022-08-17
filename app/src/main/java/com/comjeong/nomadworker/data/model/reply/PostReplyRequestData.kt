package com.comjeong.nomadworker.data.model.reply

import com.google.gson.annotations.SerializedName

data class PostReplyRequestData(
    @SerializedName("f_id") val feedId: Long,
    @SerializedName("u_id") val userId: Long,
    @SerializedName("r_content") val replyContent: String,
    @SerializedName("r_date") val timeDate: String
)

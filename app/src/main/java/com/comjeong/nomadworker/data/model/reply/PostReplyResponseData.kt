package com.comjeong.nomadworker.data.model.reply

import com.google.gson.annotations.SerializedName

data class PostReplyResponseData(
    @SerializedName("message") val message: String,
    @SerializedName("status") val status: Int,
    @SerializedName("data") val data: Result?
) {
    data class Result(
        @SerializedName("reply") val reply: Reply?
    ) {
        data class Reply(
            @SerializedName("r_id") val replyId: Long?,
            @SerializedName("r_content") val replyContent: String?,
            @SerializedName("r_date") val replyDate: String?,
            @SerializedName("u_id") val userId: Long?,
            @SerializedName("u_nickname") val userNickname: String?,
            @SerializedName("u_image") val userImage: String?
        )
    }
}

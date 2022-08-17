package com.comjeong.nomadworker.data.model.reply

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class GetReplyResponseData(
    @SerializedName("message") val message: String,
    @SerializedName("status") val status: Int,
    @SerializedName("data") val data: Result?
) {
    data class Result(
        @SerializedName("f_content") val feedContent: String,
        @SerializedName("u_nickname") val userNickname: String,
        @SerializedName("u_image") val userImage: String,
        @SerializedName("p_name") val placeName: String,
        @SerializedName("reply") val reply: List<Other>?
    ) {
        data class Other(
            @SerializedName("r_id") val replyId: Long,
            @SerializedName("r_content") val replyContent: String,
            @SerializedName("r_date") val replyDate: String,
            @SerializedName("u_id") val userId: Long,
            @SerializedName("u_nickname") val userNickname: String,
            @SerializedName("u_image") val userImage: String
        )
    }
}

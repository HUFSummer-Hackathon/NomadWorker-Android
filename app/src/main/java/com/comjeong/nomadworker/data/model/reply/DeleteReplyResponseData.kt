package com.comjeong.nomadworker.data.model.reply

import com.google.gson.annotations.SerializedName

data class DeleteReplyResponseData(
    @SerializedName("message") val message: String,
    @SerializedName("status") val status: Int
)

package com.comjeong.nomadworker.data.model.reply

import com.google.gson.annotations.SerializedName

data class DeleteReplyRequestData(
    @SerializedName("r_id") val replyId: Long
)

package com.comjeong.nomadworker.domain.model.reply

import java.time.LocalDateTime

data class PostReplyResult(
    val message: String,
    val status: Int,
    val data: Result?
) {
    data class Result(
        val reply: Reply?
    ) {
        data class Reply(
            val replyId: Long?,
            val replyContent: String?,
            val replyDate: String?,
            val userId: Long?,
            val userNickname: String?,
            val userImage: String?
        )
    }
}


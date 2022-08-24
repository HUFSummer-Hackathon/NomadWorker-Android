package com.comjeong.nomadworker.domain.model.reply

import java.sql.Time
import java.time.LocalDateTime

data class GetReplyResult(
    val message: String,
    val status: Int,
    val data: Result
) {
    data class Result(
        val feedContent: String,
        val userNickname: String,
        val userImage: String,
        val placeName: String,
        val reply: List<Other>?
    ) {
        data class Other(
            val replyId: Long?,
            val replyContent: String?,
            val replyDate: String?,
            val userId: Long?,
            val userNickname: String?,
            val userImage: String?
        )
    }
}
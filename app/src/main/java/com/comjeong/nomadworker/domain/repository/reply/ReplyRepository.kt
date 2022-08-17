package com.comjeong.nomadworker.domain.repository.reply

import com.comjeong.nomadworker.data.model.reply.DeleteReplyRequestData
import com.comjeong.nomadworker.data.model.reply.PostReplyRequestData
import com.comjeong.nomadworker.domain.model.reply.DeleteReplyResult
import com.comjeong.nomadworker.domain.model.reply.GetReplyResult
import com.comjeong.nomadworker.domain.model.reply.PostReplyResult

interface ReplyRepository {
    suspend fun getReply(feedId: Long) : GetReplyResult

    suspend fun postReply(body: PostReplyRequestData) : PostReplyResult

    suspend fun deleteReply(body: DeleteReplyRequestData) : DeleteReplyResult
}
package com.comjeong.nomadworker.data.datasource.source.reply

import com.comjeong.nomadworker.data.model.reply.*

interface ReplyDataSource {
    suspend fun getReply(feedId: Long): GetReplyResponseData

    suspend fun postReply(body: PostReplyRequestData): PostReplyResponseData

    suspend fun deleteReply(body: DeleteReplyRequestData): DeleteReplyResponseData
}
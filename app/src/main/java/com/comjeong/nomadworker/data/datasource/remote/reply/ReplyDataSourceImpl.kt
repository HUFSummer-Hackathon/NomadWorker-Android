package com.comjeong.nomadworker.data.datasource.remote.reply

import com.comjeong.nomadworker.data.datasource.source.reply.ReplyDataSource
import com.comjeong.nomadworker.data.model.reply.*
import com.comjeong.nomadworker.data.network.api.ReplyApi

class ReplyDataSourceImpl(private val api: ReplyApi): ReplyDataSource {
    override suspend fun getReply(feedId: Long): GetReplyResponseData {
        return api.getReply(feedId)
    }

    override suspend fun postReply(body: PostReplyRequestData): PostReplyResponseData {
        return api.postReply(body)
    }

    override suspend fun deleteReply(body: DeleteReplyRequestData): DeleteReplyResponseData {
        return api.deleteReply(body)
    }
}
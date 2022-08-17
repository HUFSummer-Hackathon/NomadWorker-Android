package com.comjeong.nomadworker.data.repository.reply

import com.comjeong.nomadworker.data.datasource.source.reply.ReplyDataSource
import com.comjeong.nomadworker.data.mapper.ReplyMapper
import com.comjeong.nomadworker.data.model.reply.DeleteReplyRequestData
import com.comjeong.nomadworker.data.model.reply.PostReplyRequestData
import com.comjeong.nomadworker.domain.model.reply.DeleteReplyResult
import com.comjeong.nomadworker.domain.model.reply.GetReplyResult
import com.comjeong.nomadworker.domain.model.reply.PostReplyResult
import com.comjeong.nomadworker.domain.repository.reply.ReplyRepository

class ReplyRepositoryImpl(private val datasource: ReplyDataSource): ReplyRepository {

    override suspend fun getReply(feedId: Long): GetReplyResult {
        return ReplyMapper.mapToGetReply(datasource.getReply(feedId))
    }

    override suspend fun postReply(body: PostReplyRequestData): PostReplyResult {
        return ReplyMapper.mapToPostReply(datasource.postReply(body))
    }

    override suspend fun deleteReply(body: DeleteReplyRequestData): DeleteReplyResult {
        return ReplyMapper.mapToDeleteReply(datasource.deleteReply(body))
    }
}
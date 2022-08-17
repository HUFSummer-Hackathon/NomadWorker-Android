package com.comjeong.nomadworker.data.mapper

import com.comjeong.nomadworker.data.model.reply.DeleteReplyResponseData
import com.comjeong.nomadworker.data.model.reply.GetReplyResponseData
import com.comjeong.nomadworker.data.model.reply.PostReplyResponseData
import com.comjeong.nomadworker.domain.model.reply.DeleteReplyResult
import com.comjeong.nomadworker.domain.model.reply.GetReplyResult
import com.comjeong.nomadworker.domain.model.reply.PostReplyResult

object ReplyMapper {
    fun mapToGetReply(body: GetReplyResponseData): GetReplyResult {
        return GetReplyResult(
            message = body.message,
            status = body.status,
            data = GetReplyResult.Result(
                feedContent = body.data?.feedContent,
                userNickname = body.data?.userNickname,
                userImage = body.data?.userImage,
                placeName = body.data?.placeName,
                reply = body.data?.reply?.map { reply ->
                    GetReplyResult.Result.Other(
                        replyId = reply.replyId,
                        replyContent = reply.replyContent,
                        replyDate = reply.replyDate,
                        userId = reply.userId,
                        userNickname = reply.userNickname,
                        userImage = reply.userImage
                    )
                }
            )
        )
    }

    fun mapToPostReply(body: PostReplyResponseData): PostReplyResult {
        return PostReplyResult(
            message = body.message,
            status = body.status,
            data = PostReplyResult.Result(
                reply = PostReplyResult.Result.Reply(
                    replyId = body.data?.reply?.replyId,
                    replyContent = body.data?.reply?.replyContent,
                    replyDate = body.data?.reply?.replyDate,
                    userId = body.data?.reply?.userId,
                    userNickname = body.data?.reply?.userNickname,
                    userImage = body.data?.reply?.userImage
                )
            )
        )
    }

    fun mapToDeleteReply(body: DeleteReplyResponseData): DeleteReplyResult {
        return DeleteReplyResult(
            message = body.message,
            status = body.status
        )
    }
}
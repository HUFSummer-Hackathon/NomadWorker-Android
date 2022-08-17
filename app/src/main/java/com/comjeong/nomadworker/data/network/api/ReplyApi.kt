package com.comjeong.nomadworker.data.network.api

import com.comjeong.nomadworker.data.model.reply.*
import retrofit2.http.*

interface ReplyApi {

    @GET("feeds/reply")
    suspend fun getReply(
        @Query("f_id") feedId: Long
    ) : GetReplyResponseData

    @POST("feeds/reply")
    suspend fun postReply(
        @Body body: PostReplyRequestData
    ) : PostReplyResponseData

    @HTTP(method = "DELETE", path = "feeds/reply", hasBody = true)
    suspend fun deleteReply(
        @Body body: DeleteReplyRequestData
    ): DeleteReplyResponseData

}
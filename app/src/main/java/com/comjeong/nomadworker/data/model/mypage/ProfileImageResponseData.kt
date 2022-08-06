package com.comjeong.nomadworker.data.model.mypage

import com.google.gson.annotations.SerializedName

data class ProfileImageResponseData(
    @SerializedName("message") val message: String,
    @SerializedName("status") val status: Int
)

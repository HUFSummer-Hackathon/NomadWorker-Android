package com.comjeong.nomadworker.data.model.place

import com.google.gson.annotations.SerializedName

data class PlaceScrapResponseData(
    @SerializedName("message") val message: String,
    @SerializedName("status") val status: Int
)

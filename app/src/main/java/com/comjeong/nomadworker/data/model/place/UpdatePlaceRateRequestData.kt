package com.comjeong.nomadworker.data.model.place

import com.google.gson.annotations.SerializedName

data class UpdatePlaceRateRequestData(
    @SerializedName("placeId") val placeId: Long,
    @SerializedName("placeGrade") val placeRate: Float
)
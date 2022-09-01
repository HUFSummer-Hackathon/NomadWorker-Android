package com.comjeong.nomadworker.data.model.place

import com.google.gson.annotations.SerializedName

data class PlaceScrapRequestData(
    @SerializedName("u_id") val userId: Long,
    @SerializedName("p_id") val placeId: Long
)

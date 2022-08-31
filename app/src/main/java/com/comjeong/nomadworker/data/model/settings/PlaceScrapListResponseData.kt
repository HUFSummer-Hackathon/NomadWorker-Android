package com.comjeong.nomadworker.data.model.settings

import com.google.gson.annotations.SerializedName

data class PlaceScrapListResponseData(
    @SerializedName("message") val message: String,
    @SerializedName("status") val status: Int,
    @SerializedName("data") val data: List<Result>?
) {
    data class Result(
        @SerializedName("u_p_id") val userPlaceId: Long,
        @SerializedName("p_id") val placeId: Long,
        @SerializedName("place_name") val placeName: String,
        @SerializedName("place_address") val placeAddress: String,
        @SerializedName("place_weektime") val placeWeekTime: String,
        @SerializedName("place_weekendtime") val placeWeekendTime: String,
        @SerializedName("placeGrade") val placeRate: Float,
        @SerializedName("p_image") val placeThumbnailImage: String
    )
}

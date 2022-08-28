package com.comjeong.nomadworker.data.model.search

import com.google.gson.annotations.SerializedName

data class PlaceSearchResponseData(
    @SerializedName("message") val message: String,
    @SerializedName("status") val status: Int,
    @SerializedName("data") val placeInfo: List<PlaceList>?
) {
    data class PlaceList(
        @SerializedName("p_id") val placeId: Long,
        @SerializedName("place_name") val placeName: String,
        @SerializedName("place_address") val placeAddress: String,
        @SerializedName("place_weektime") val placeWeekTime: String,
        @SerializedName("place_weekendtime") val placeWeekendTime: String,
        @SerializedName("placeGrade") val placeRate: Float,
        @SerializedName("p_image") val placeImage: String
    )
}
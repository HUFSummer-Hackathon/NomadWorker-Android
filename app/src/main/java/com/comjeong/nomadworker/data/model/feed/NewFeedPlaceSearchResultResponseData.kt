package com.comjeong.nomadworker.data.model.feed

import com.google.gson.annotations.SerializedName

data class NewFeedPlaceSearchResultResponseData(
    @SerializedName("message") val message : String,
    @SerializedName("status") val status : Int,
    @SerializedName("data") val placeList : List<Result>?
) {
    data class Result(
        @SerializedName("p_id") val placeId : Long,
        @SerializedName("place_name") val placeName : String
    )
}

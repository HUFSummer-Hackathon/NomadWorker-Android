package com.comjeong.nomadworker.domain.model.settings

data class PlaceScrapResult(
    val message: String,
    val status: Int,
    val data: List<Result>?
) {
    data class Result(
        val userPlaceId: Long,
        val placeId: Long,
        val placeName: String,
        val placeAddress: String,
        val placeWeekTime: String,
        val placeWeekendTime: String,
        val placeRate: Float,
        val placeThumbnailImage: String
    )
}

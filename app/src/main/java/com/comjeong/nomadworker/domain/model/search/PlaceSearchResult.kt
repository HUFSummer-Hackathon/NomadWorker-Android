package com.comjeong.nomadworker.domain.model.search

data class PlaceSearchResult(
    val message: String,
    val status: Int,
    val data: List<PlaceList>?
) {
    data class PlaceList(
        val placeId: Long,
        val placeName: String,
        val placeAddress: String,
        val placeWeekTime: String,
        val placeWeekendTime: String,
        val placeImage: String
    )
}
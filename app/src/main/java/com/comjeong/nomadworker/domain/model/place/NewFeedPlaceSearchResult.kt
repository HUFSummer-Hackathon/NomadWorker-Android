package com.comjeong.nomadworker.domain.model.place

data class NewFeedPlaceSearchResult(
    val message : String,
    val status : Int,
    val placeList : List<Result>?
) {
    data class Result(
        val placeId : Long,
        val placeName : String
    )
}

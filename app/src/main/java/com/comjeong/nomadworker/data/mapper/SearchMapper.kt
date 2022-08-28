package com.comjeong.nomadworker.data.mapper

import com.comjeong.nomadworker.data.model.search.PlaceSearchResponseData
import com.comjeong.nomadworker.domain.model.search.PlaceSearchResult

object SearchMapper {
    fun mapToPlaceSearchResult(body: PlaceSearchResponseData): PlaceSearchResult {
        return PlaceSearchResult(
            message = body.message,
            status = body.status,
            data = body.placeInfo?.map { placeInfo ->
                PlaceSearchResult.PlaceList(
                    placeId = placeInfo.placeId,
                    placeName = placeInfo.placeName,
                    placeAddress = placeInfo.placeAddress,
                    placeWeekTime = placeInfo.placeWeekTime,
                    placeWeekendTime = placeInfo.placeWeekendTime,
                    placeRate = placeInfo.placeRate,
                    placeImage = placeInfo.placeImage
                )
            }
        )
    }
}
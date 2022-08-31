package com.comjeong.nomadworker.data.mapper

import com.comjeong.nomadworker.data.model.settings.PlaceScrapListResponseData
import com.comjeong.nomadworker.domain.model.settings.PlaceScrapListResult

object SettingsMapper {

    fun mapToPlaceScrapResult(body: PlaceScrapListResponseData): PlaceScrapListResult {
        return PlaceScrapListResult(
            message = body.message,
            status = body.status,
            data = body.data?.map { result ->
                PlaceScrapListResult.Result(
                    userPlaceId = result.userPlaceId,
                    placeId = result.placeId,
                    placeName = result.placeName,
                    placeAddress = result.placeAddress,
                    placeWeekTime = result.placeWeekTime,
                    placeWeekendTime = result.placeWeekendTime,
                    placeRate = result.placeRate,
                    placeThumbnailImage = result.placeThumbnailImage
                )
            }
        )
    }
}
package com.comjeong.nomadworker.data.mapper

import com.comjeong.nomadworker.data.model.settings.PlaceScrapResponseData
import com.comjeong.nomadworker.domain.model.settings.PlaceScrapResult

object SettingsMapper {

    fun mapToPlaceScrapResult(body: PlaceScrapResponseData): PlaceScrapResult {
        return PlaceScrapResult(
            message = body.message,
            status = body.status,
            data = body.data?.map { result ->
                PlaceScrapResult.Result(
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
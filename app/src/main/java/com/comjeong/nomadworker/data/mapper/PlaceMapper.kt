package com.comjeong.nomadworker.data.mapper

import com.comjeong.nomadworker.data.model.place.LocationPlaceResponseData
import com.comjeong.nomadworker.data.model.place.PlaceDetailResponseData
import com.comjeong.nomadworker.data.model.place.PlaceScrapResponseData
import com.comjeong.nomadworker.data.model.place.UpdatePlaceRateResponseData
import com.comjeong.nomadworker.domain.model.place.LocationPlaceResult
import com.comjeong.nomadworker.domain.model.place.PlaceDetailResult
import com.comjeong.nomadworker.domain.model.place.PlaceScrapResult
import com.comjeong.nomadworker.domain.model.place.UpdatePlaceRateResult

object PlaceMapper {
    fun mapToLocationPlaceResult(body: LocationPlaceResponseData): LocationPlaceResult {
        return LocationPlaceResult(
            message = body.message,
            status = body.status,
            data = LocationPlaceResult.Result(
                locationName = body.data?.locationName,
                place = body.data?.place?.map { place ->
                    LocationPlaceResult.Result.Place(
                        placeId = place.placeId,
                        placeName = place.placeName,
                        placeAddress = place.placeAddress,
                        placeWeekTime = place.placeWeekTime,
                        placeWeekEndTime = place.placeWeekEndTime,
                        placeRate = place.placeRate,
                        placeThumbnailImageUrl = place.placeThumbnailImageUrl
                    )
                }
            )
        )
    }

    fun mapToPlaceDetailResult(body: PlaceDetailResponseData): PlaceDetailResult {
        return PlaceDetailResult(
            message = body.message,
            status = body.status,
            data = PlaceDetailResult.Result(
                placeId = body.data?.placeId,
                placeCategory = body.data?.placeCategory,
                placeName = body.data?.placeName,
                placeWeekTime = body.data?.placeWeekTime,
                placeWeekendTime = body.data?.placeWeekendTime,
                placeRate = body.data?.placeRate,
                placeAddress = body.data?.placeAddress,
                placeImageUrl = body.data?.placeImageUrl,
                placeLatitude = body.data?.placeLatitude,
                placeLongitude = body.data?.placeLongitude,
                placeStoreType = body.data?.placeStoreType,
                isScraped = if (body.data?.isScraped == true) 1 else 0,
                placeUrl = body.data?.placeUrl
            )
        )
    }

    fun mapToUpdatePlaceRateResult(body: UpdatePlaceRateResponseData): UpdatePlaceRateResult {
        return UpdatePlaceRateResult(
            message = body.message,
            status = body.status
        )
    }

    fun mapToPlaceScrapResult(body: PlaceScrapResponseData): PlaceScrapResult {
        return PlaceScrapResult(
            message = body.message,
            status = body.status
        )
    }
}
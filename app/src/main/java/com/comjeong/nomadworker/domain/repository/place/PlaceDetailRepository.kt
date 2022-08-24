package com.comjeong.nomadworker.domain.repository.place

import com.comjeong.nomadworker.data.model.place.UpdatePlaceRateRequestData
import com.comjeong.nomadworker.domain.model.place.PlaceDetailResult
import com.comjeong.nomadworker.domain.model.place.UpdatePlaceRateResult

interface PlaceDetailRepository {

    suspend fun getPlaceDetailById(placeId: Long) : PlaceDetailResult

    suspend fun updatePlaceRate(body: UpdatePlaceRateRequestData): UpdatePlaceRateResult
}
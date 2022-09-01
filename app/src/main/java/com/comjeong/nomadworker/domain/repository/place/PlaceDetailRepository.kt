package com.comjeong.nomadworker.domain.repository.place

import com.comjeong.nomadworker.data.model.place.PlaceScrapRequestData
import com.comjeong.nomadworker.data.model.place.UpdatePlaceRateRequestData
import com.comjeong.nomadworker.domain.model.place.PlaceDetailResult
import com.comjeong.nomadworker.domain.model.place.PlaceScrapResult
import com.comjeong.nomadworker.domain.model.place.UpdatePlaceRateResult

interface PlaceDetailRepository {

    suspend fun getPlaceDetailById(placeId: Long) : PlaceDetailResult

    suspend fun updatePlaceRate(body: UpdatePlaceRateRequestData): UpdatePlaceRateResult

    suspend fun postPlaceScrap(body: PlaceScrapRequestData): PlaceScrapResult
}
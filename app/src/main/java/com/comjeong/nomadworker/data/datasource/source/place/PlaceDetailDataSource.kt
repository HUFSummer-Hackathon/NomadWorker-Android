package com.comjeong.nomadworker.data.datasource.source.place

import com.comjeong.nomadworker.data.model.place.PlaceDetailResponseData
import com.comjeong.nomadworker.data.model.place.UpdatePlaceRateRequestData
import com.comjeong.nomadworker.data.model.place.UpdatePlaceRateResponseData

interface PlaceDetailDataSource {

    suspend fun getPlaceDetailById(placeId: Long): PlaceDetailResponseData

    suspend fun updatePlaceRate(body: UpdatePlaceRateRequestData): UpdatePlaceRateResponseData
}
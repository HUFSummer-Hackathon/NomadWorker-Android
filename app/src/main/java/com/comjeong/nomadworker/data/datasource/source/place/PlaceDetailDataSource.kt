package com.comjeong.nomadworker.data.datasource.source.place

import com.comjeong.nomadworker.data.model.place.*

interface PlaceDetailDataSource {

    suspend fun getPlaceDetailById(placeId: Long): PlaceDetailResponseData

    suspend fun updatePlaceRate(body: UpdatePlaceRateRequestData): UpdatePlaceRateResponseData

    suspend fun postPlaceScrap(body: PlaceScrapRequestData): PlaceScrapResponseData
}
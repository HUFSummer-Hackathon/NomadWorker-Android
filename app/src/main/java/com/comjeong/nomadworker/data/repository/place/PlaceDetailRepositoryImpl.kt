package com.comjeong.nomadworker.data.repository.place

import com.comjeong.nomadworker.data.datasource.source.place.PlaceDetailDataSource
import com.comjeong.nomadworker.data.mapper.PlaceMapper
import com.comjeong.nomadworker.data.model.place.PlaceScrapRequestData
import com.comjeong.nomadworker.data.model.place.UpdatePlaceRateRequestData
import com.comjeong.nomadworker.domain.model.place.PlaceDetailResult
import com.comjeong.nomadworker.domain.model.place.PlaceScrapResult
import com.comjeong.nomadworker.domain.model.place.UpdatePlaceRateResult
import com.comjeong.nomadworker.domain.repository.place.PlaceDetailRepository

class PlaceDetailRepositoryImpl(private val dataSource: PlaceDetailDataSource) : PlaceDetailRepository {
    override suspend fun getPlaceDetailById(placeId: Long): PlaceDetailResult {
        return PlaceMapper.mapToPlaceDetailResult(dataSource.getPlaceDetailById(placeId))
    }

    override suspend fun updatePlaceRate(body: UpdatePlaceRateRequestData): UpdatePlaceRateResult {
        return PlaceMapper.mapToUpdatePlaceRateResult(dataSource.updatePlaceRate(body))
    }

    override suspend fun postPlaceScrap(body: PlaceScrapRequestData): PlaceScrapResult {
        return PlaceMapper.mapToPlaceScrapResult(dataSource.postPlaceScrap(body))
    }
}
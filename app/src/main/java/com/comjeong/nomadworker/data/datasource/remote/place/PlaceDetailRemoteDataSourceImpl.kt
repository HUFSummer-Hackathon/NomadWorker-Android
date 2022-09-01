package com.comjeong.nomadworker.data.datasource.remote.place

import com.comjeong.nomadworker.data.datasource.source.place.PlaceDetailDataSource
import com.comjeong.nomadworker.data.model.place.*
import com.comjeong.nomadworker.data.network.api.AuthApi
import com.comjeong.nomadworker.data.network.api.PlaceApi

class PlaceDetailRemoteDataSourceImpl(private val placeApi: PlaceApi, private val authApi: AuthApi) : PlaceDetailDataSource {

    override suspend fun getPlaceDetailById(placeId: Long): PlaceDetailResponseData {
        return placeApi.getPlaceDetailById(placeId)
    }

    override suspend fun updatePlaceRate(body: UpdatePlaceRateRequestData): UpdatePlaceRateResponseData {
        return placeApi.putPlaceRate(body)
    }

    override suspend fun postPlaceScrap(body: PlaceScrapRequestData): PlaceScrapResponseData {
        return authApi.postPlaceScrap(body)
    }
}
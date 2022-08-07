package com.comjeong.nomadworker.data.datasource.remote.search

import com.comjeong.nomadworker.data.datasource.source.search.PlaceSearchDataSource
import com.comjeong.nomadworker.data.model.search.PlaceSearchResponseData
import com.comjeong.nomadworker.data.network.api.PlaceApi

class PlaceSearchDataSourceImpl(private val api: PlaceApi): PlaceSearchDataSource {
    override suspend fun getPlaceSearchResult(location: String, storetype: String, placeName: String): PlaceSearchResponseData {
        return api.getPlaceSearchResult(location,storetype,placeName)
    }
}
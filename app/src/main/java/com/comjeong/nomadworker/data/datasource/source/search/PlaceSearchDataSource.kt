package com.comjeong.nomadworker.data.datasource.source.search

import com.comjeong.nomadworker.data.model.search.PlaceSearchResponseData

interface PlaceSearchDataSource {
    suspend fun getPlaceSearchResult(location: String, storetype: String, placeName: String): PlaceSearchResponseData
}
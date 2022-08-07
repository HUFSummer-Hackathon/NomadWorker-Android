package com.comjeong.nomadworker.data.repository.search

import com.comjeong.nomadworker.data.datasource.source.search.PlaceSearchDataSource
import com.comjeong.nomadworker.data.mapper.SearchMapper
import com.comjeong.nomadworker.domain.model.search.PlaceSearchResult
import com.comjeong.nomadworker.domain.repository.search.PlaceSearchRepository

class PlaceSearchRepositoryImpl(private val datasource: PlaceSearchDataSource): PlaceSearchRepository {
    override suspend fun getPlaceSearchResult(location: String, storetype: String, placeName: String): PlaceSearchResult {
        return SearchMapper.mapToPlaceSearchResult(datasource.getPlaceSearchResult(location,storetype,placeName))
    }
}
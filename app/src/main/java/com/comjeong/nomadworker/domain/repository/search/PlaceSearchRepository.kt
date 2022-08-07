package com.comjeong.nomadworker.domain.repository.search

import com.comjeong.nomadworker.domain.model.search.PlaceSearchResult

interface PlaceSearchRepository {
    suspend fun getPlaceSearchResult(location: String, storetype: String, placeName: String) : PlaceSearchResult
}
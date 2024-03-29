package com.comjeong.nomadworker.data.network.api

import com.comjeong.nomadworker.data.model.home.LocationCategoryResponseData
import com.comjeong.nomadworker.data.model.home.RecommendPlaceResponseData
import com.comjeong.nomadworker.data.model.home.UpdateCurrentLocationRequestData
import com.comjeong.nomadworker.data.model.home.UpdateCurrentLocationResponseData
import com.comjeong.nomadworker.data.model.place.*
import com.comjeong.nomadworker.data.model.search.PlaceSearchResponseData
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query

interface PlaceApi {

    @PUT("user/geographic")
    suspend fun updateCurrentLocation(
        @Body body : UpdateCurrentLocationRequestData
    ) : UpdateCurrentLocationResponseData

    @GET("place/category")
    suspend fun getLocationCategory(): LocationCategoryResponseData

    @GET("place/location")
    suspend fun getPlaceByLocationName(
        @Query("locationCategory") locationName: String
    ): LocationPlaceResponseData

    @GET("place/detail")
    suspend fun getPlaceDetailById(
        @Query("placeId") placeId: Long
    ): PlaceDetailResponseData

    @GET("place/near")
    suspend fun getNearbyPlace(
        @Query("latitude") latitude: Float,
        @Query("longitude") longitude: Float
    ): NearbyPlaceResponseData

    @GET("place/recommend")
    suspend fun getRecommendPlace(): RecommendPlaceResponseData

    @GET("search/place")
    suspend fun getPlaceSearchResult(
        @Query("place_cat") location: String,
        @Query("place_Storetype") storetype: String,
        @Query("place_name") placeName: String
    ): PlaceSearchResponseData

    @PUT("place/grade")
    suspend fun putPlaceRate(
        @Body body: UpdatePlaceRateRequestData
    ): UpdatePlaceRateResponseData
}


package com.comjeong.nomadworker.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comjeong.nomadworker.common.Event
import com.comjeong.nomadworker.domain.model.place.NewFeedPlaceSearchResult
import com.comjeong.nomadworker.domain.model.search.PlaceSearchResult
import com.comjeong.nomadworker.domain.repository.search.PlaceSearchRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class SearchViewModel(private val repository: PlaceSearchRepository): ViewModel() {

    private var _placeName: String = ""
    var placeName : String = _placeName
        set(value){
            _placeName = value
            field = value
        }

    private var _location: String = ""
    var location: String = _location
        set(value){
            _location = value
            field = value
        }

    private var _storetype: String = ""
    var storetype: String = _storetype
        set(value){
            _storetype = value
            field = value
        }

    private val _placeList: MutableLiveData<List<PlaceSearchResult.PlaceList>> = MutableLiveData<List<PlaceSearchResult.PlaceList>>()
    val placeList: LiveData<List<PlaceSearchResult.PlaceList>> = _placeList

    private val _openPlaceDetailEvent: MutableLiveData<Event<Long>> = MutableLiveData<Event<Long>>()
    val openPlaceDetailEvent: LiveData<Event<Long>> = _openPlaceDetailEvent

    fun getPlaceSearchResult() {
        Timber.d("Call search/place API -> placeName: $_placeName location: $_location storetype: $_storetype")
        viewModelScope.launch {
            try {
                val response = repository.getPlaceSearchResult(_location,_storetype,_placeName)

                when (response.status) {
                    200 -> {
                        _placeList.value = response.data
                    }
                    400 -> {
                        _placeList.value = emptyList()
                    }
                }
                Timber.d("SUCCESS: $response")
            } catch (e: Throwable) {
                Timber.d("FAILED: $e")
            }
        }
    }

    fun openPlaceDetailByPlaceId(placeId: Long) {
        _openPlaceDetailEvent.value = Event(placeId)
    }

}
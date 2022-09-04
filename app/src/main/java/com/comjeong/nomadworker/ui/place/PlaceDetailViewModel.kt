package com.comjeong.nomadworker.ui.place

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comjeong.nomadworker.common.Event
import com.comjeong.nomadworker.data.datasource.local.NomadSharedPreferences
import com.comjeong.nomadworker.data.model.place.PlaceScrapRequestData
import com.comjeong.nomadworker.data.model.place.UpdatePlaceRateRequestData
import com.comjeong.nomadworker.domain.model.place.PlaceDetailResult
import com.comjeong.nomadworker.domain.repository.place.PlaceDetailRepository
import com.comjeong.nomadworker.ui.common.extension.default
import kotlinx.coroutines.launch
import timber.log.Timber

class PlaceDetailViewModel(private val repository: PlaceDetailRepository) : ViewModel() {

    private var _placeId: Long = -1
    var placeId: Long = _placeId
        set(value) {
            _placeId = value
            field = value
        }

    private var _placeRate: Float = 0F
    var placeRate: Float = _placeRate
        set(value) {
            _placeRate = value
            field = value
        }

    var isScraped = MutableLiveData<Int>().default(0)

    private val _placeDetailInfo: MutableLiveData<PlaceDetailResult.Result> =
        MutableLiveData<PlaceDetailResult.Result>()
    val placeDetailInfo: LiveData<PlaceDetailResult.Result> = _placeDetailInfo

    private val _message: MutableLiveData<Event<String>> = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>> = _message

    fun getPlaceDetailByPlaceId() {
        viewModelScope.launch {
            try {
                val response = repository.getPlaceDetailById(_placeId)

                when (response.status) {
                    200 -> {
                        _placeDetailInfo.value = response.data
                        isScraped.value = if (response.data?.isScraped == 0) 0 else 1
                    }
                    400 -> {
                        _placeDetailInfo.value = response.data
                    }
                }

                Timber.d("SUCCESS: $response")
            } catch (e: Throwable) {
                Timber.d("FAILED: $e")
            }
        }
    }

    fun updatePlaceRate() {
        val requestBody = UpdatePlaceRateRequestData(
            placeId = _placeId,
            placeRate = placeRate
        )
        Timber.d("$requestBody")

        viewModelScope.launch {
            try {
                val response = repository.updatePlaceRate(requestBody)

                when (response.status) {
                    400 -> {
                        _message.value = Event(response.message)
                    }
                }
                Timber.d("SUCCESS: $response")
            } catch (e: Throwable) {
                Timber.d("FAILED: $e")
                _message.value = Event("연결이 불안정합니다. 잠시 후에 시도해주세요 :(")
            }
        }
    }

    fun postPlaceScrap() {
        val requestBody = PlaceScrapRequestData(
            userId = NomadSharedPreferences.getUserId(),
            placeId = _placeId,
            isScraped = isScraped.value != 0
        )
        viewModelScope.launch {
            runCatching { repository.postPlaceScrap(requestBody) }
                .onSuccess {
                    if (it.status == 200) {
                        isScraped.value = isScraped.value?.plus(1)?.rem(2)

                        Timber.d("SUCCESS: ${it.message}")
                    }
                }
                .onFailure {
                    Timber.d("FAILED: $it")
                }
        }
    }
}
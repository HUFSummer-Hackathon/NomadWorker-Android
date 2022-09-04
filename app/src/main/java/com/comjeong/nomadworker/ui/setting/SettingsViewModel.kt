package com.comjeong.nomadworker.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comjeong.nomadworker.common.Event
import com.comjeong.nomadworker.common.UiState
import com.comjeong.nomadworker.data.datasource.local.NomadSharedPreferences
import com.comjeong.nomadworker.domain.model.settings.PlaceScrapListResult
import com.comjeong.nomadworker.domain.repository.settings.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import timber.log.Timber

class SettingsViewModel(private val repository: SettingsRepository) : ViewModel() {

    private var _profileImage: MultipartBody.Part =
        MultipartBody.Part.createFormData("image", "file")
    var profileImage: MultipartBody.Part = _profileImage
        set(value) {
            _profileImage = value
            field = value
        }

    private val _message: MutableLiveData<Event<String>> = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>> = _message

    private val _uiState = MutableStateFlow<UiState<PlaceScrapListResult.Result>>(UiState.Loading)
    val uiState: StateFlow<UiState<PlaceScrapListResult.Result>> = _uiState.asStateFlow()

    private val _openPlaceDetailEvent: MutableLiveData<Event<Long>> = MutableLiveData<Event<Long>>()
    val openPlaceDetailEvent: LiveData<Event<Long>> = _openPlaceDetailEvent

    fun updateUserProfileImage() {
        Timber.d("TEST $_profileImage")
        viewModelScope.launch {
            try {
                val response = repository.updateUserProfileImage(_profileImage)

                when (response.status) {
                    200 -> {
                        // 업로드 성공, 갱신
                        _message.value = Event(response.message)
                    }
                    400 -> {
                        _message.value = Event(response.message)
                    }
                }
                Timber.d("SUCCESS: $response")
            } catch (e: Throwable) {
                Timber.d("FAILED: $e")
            }
        }
    }

    fun getPlaceScrapListByUserId() {
        viewModelScope.launch {
            runCatching { repository.getPlaceScrapListByUserId(NomadSharedPreferences.getUserId()) }
                .onSuccess { response ->
                    if (response.data == null) _uiState.value = UiState.Empty
                    else _uiState.value = UiState.Success(response.data)
                    Timber.d("$response")
                }
                .onFailure {
                    _uiState.value = UiState.Error(it.message)
                    Timber.d("$it")
                }
        }
    }

    fun openPlaceDetailByPlaceId(placeId: Long) {
        _openPlaceDetailEvent.value = Event(placeId)
    }
}
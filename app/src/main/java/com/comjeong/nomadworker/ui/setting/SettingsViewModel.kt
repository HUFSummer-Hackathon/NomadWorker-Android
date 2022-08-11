package com.comjeong.nomadworker.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comjeong.nomadworker.common.Event
import com.comjeong.nomadworker.domain.repository.settings.SettingsRepository
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import timber.log.Timber

class SettingsViewModel(private val repository: SettingsRepository) : ViewModel() {

    private var _profileImage: MultipartBody.Part = MultipartBody.Part.createFormData("image", "file")
    var profileImage: MultipartBody.Part = _profileImage
        set(value) {
            _profileImage = value
            field = value
        }


    private val _message: MutableLiveData<Event<String>> = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>> = _message

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
}
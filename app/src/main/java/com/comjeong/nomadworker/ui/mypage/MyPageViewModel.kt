package com.comjeong.nomadworker.ui.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comjeong.nomadworker.common.Event
import com.comjeong.nomadworker.domain.model.feed.UserTotalFeedsWithInfoResult
import com.comjeong.nomadworker.domain.model.mypage.UserFeedDetailResult
import com.comjeong.nomadworker.domain.repository.mypage.MyPageRepository
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import timber.log.Timber

class MyPageViewModel(private val repository: MyPageRepository) : ViewModel() {

    private var _feedId: Long = 0
    var feedId: Long = _feedId
        set(value) {
            _feedId = value
            field = value
        }

    private var _userId: Long = 0
    var userId: Long = _userId
        set(value) {
            _userId = value
            field = value
        }

    private var _profileImage: MultipartBody.Part = MultipartBody.Part.createFormData("file", "file")
    var profileImage: MultipartBody.Part = _profileImage
        set(value) {
            _profileImage = value
            field = value
        }

    private val _userFeedList: MutableLiveData<List<UserTotalFeedsWithInfoResult.Result.Feed>> = MutableLiveData<List<UserTotalFeedsWithInfoResult.Result.Feed>>()
    val userFeedList: LiveData<List<UserTotalFeedsWithInfoResult.Result.Feed>> = _userFeedList

    private val _userInfo: MutableLiveData<UserTotalFeedsWithInfoResult.Result> = MutableLiveData<UserTotalFeedsWithInfoResult.Result>()
    val userInfo: LiveData<UserTotalFeedsWithInfoResult.Result> = _userInfo

    private val _openFeedDetailEvent: MutableLiveData<Event<Long>> = MutableLiveData<Event<Long>>()
    val openFeedDetailEvent: LiveData<Event<Long>> = _openFeedDetailEvent

    private val _userFeedDetail: MutableLiveData<UserFeedDetailResult.Result> = MutableLiveData<UserFeedDetailResult.Result>()
    val userFeedDetail: LiveData<UserFeedDetailResult.Result> = _userFeedDetail

    private val _message: MutableLiveData<Event<String>> = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>> = _message

    fun getUserTotalFeedsWithInfo() {
        viewModelScope.launch {
            try {
                val response = repository.getUserTotalFeedsWithInfo(_userId)

                when (response.status) {
                    200 -> {
                        _userInfo.value = response.data
                        _userFeedList.value = response.data?.feedList
                    }
                    400 -> {
                        _userInfo.value = response.data
                        _userFeedList.value = emptyList()
                    }
                }

                Timber.d("SUCCESS: $response")
            } catch (e: Throwable) {
                Timber.d("FAILED: $e")
            }
        }
    }

    fun getUserFeedDetail() {
        viewModelScope.launch {
            try {
                val response = repository.getUserFeedDetail(_feedId)

                when (response.status) {
                    200 -> {
                        _userFeedDetail.value = response.data
                    }
                    400 -> {
                        _userFeedDetail.value = response.data
                    }
                }
                Timber.d("SUCCESS: $response")
            } catch (e: Throwable) {
                Timber.d("FAILED: $e")
            }
        }
    }

    fun updateUserProfileImage() {
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
            } catch (e: Throwable) {
                Timber.d("FAILED: $e")
            }
        }
    }

    fun openFeedDetailByFeedId(feedId: Long) {
        _openFeedDetailEvent.value = Event(feedId)
    }

}
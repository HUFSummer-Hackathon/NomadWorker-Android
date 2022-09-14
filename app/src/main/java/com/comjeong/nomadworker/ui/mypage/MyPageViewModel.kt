package com.comjeong.nomadworker.ui.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comjeong.nomadworker.common.Event
import com.comjeong.nomadworker.data.model.feed.FeedLikeRequestData
import com.comjeong.nomadworker.domain.model.feed.UserTotalFeedsWithInfoResult
import com.comjeong.nomadworker.domain.model.mypage.UserFeedDetailResult
import com.comjeong.nomadworker.domain.repository.feed.FeedRepository
import com.comjeong.nomadworker.domain.repository.mypage.MyPageRepository
import com.comjeong.nomadworker.ui.common.extension.default
import kotlinx.coroutines.launch
import timber.log.Timber

class MyPageViewModel(
    private val repository: MyPageRepository,
    private val feedRepository: FeedRepository
) : ViewModel() {

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

    private val _isSuccessDelete: MutableLiveData<Event<Boolean>> = MutableLiveData<Event<Boolean>>()
    val isSuccessDelete: MutableLiveData<Event<Boolean>> = _isSuccessDelete

    private val _userNickname: MutableLiveData<Event<String>> = MutableLiveData<Event<String>>()
    val userNickname: MutableLiveData<Event<String>> = _userNickname

    var isFavorite: MutableLiveData<Int> = MutableLiveData<Int>().default(0)

    var likesCount: MutableLiveData<Int> = MutableLiveData<Int>().default(0)

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
                        isFavorite.value = response.data?.likeStatus
                        likesCount.value = response.data?.likeCount
                        Timber.e("${likesCount.value} / ${isFavorite.value}")
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

    fun deleteFeed() {
        viewModelScope.launch {
            try {
                val response = repository.deleteFeed(_feedId)

                when(response.status) {
                    200 -> {
                        _isSuccessDelete.value = Event(true)
                    }
                    400 -> {
                        _isSuccessDelete.value = Event(false)
                    }
                }
                Timber.d("SUCCESS: $response")
            } catch (e : Throwable) {
                Timber.d("FAILED: $e")
            }
        }
    }

    fun openFeedDetailByFeedId(feedId: Long) {
        _openFeedDetailEvent.value = Event(feedId)
    }

    fun postFeedLike(feedLikeRequestData: FeedLikeRequestData) {
        viewModelScope.launch {
            try {
                val response = feedRepository.postFeedLike(feedLikeRequestData)

                when (response.status) {
                    200 -> {
                        Timber.d("좋아요 통신 성공")
                        isFavorite.value = isFavorite.value?.plus(1)?.rem(2)
                        Timber.e("${isFavorite.value}")
                        updateLikeCount()
                    }
                    400 -> {
                        Timber.d("좋아요 통신 실패")
                    }
                }
                Timber.d("SUCCESS: $response")
            } catch (e: Throwable) {
                Timber.d("FAILED: $e")
            }
        }
    }

    private fun updateLikeCount() {
        when (isFavorite.value) {
            0 -> likesCount.value = likesCount.value?.minus(1)
            else -> likesCount.value = likesCount.value?.plus(1)
        }
    }
}
package com.comjeong.nomadworker.ui.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comjeong.nomadworker.domain.model.feed.TotalFeedsResult
import com.comjeong.nomadworker.domain.model.place.NewFeedPlaceSearchResult
import com.comjeong.nomadworker.domain.repository.feed.FeedRepository
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import timber.log.Timber
import java.io.File

class FeedViewModel(private val repository: FeedRepository) : ViewModel() {

    private var _placeName: String = ""
    var placeName : String = _placeName
        set(value){
            _placeName = value
            field = value
        }

    private val _placeList: MutableLiveData<List<NewFeedPlaceSearchResult.Result>> = MutableLiveData<List<NewFeedPlaceSearchResult.Result>>()
    val placeList: LiveData<List<NewFeedPlaceSearchResult.Result>> = _placeList

    private val _isSelectPlace: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val isSelectPlace: LiveData<Boolean> = _isSelectPlace

    private val _isSuccessPost: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val isSuccessPost: LiveData<Boolean> = _isSuccessPost

    private val _feedList: MutableLiveData<List<TotalFeedsResult.Result>> = MutableLiveData<List<TotalFeedsResult.Result>>()
    val feedList: LiveData<List<TotalFeedsResult.Result>> = _feedList

    fun getTotalFeeds() {
        viewModelScope.launch {
            try {

                val response = repository.getTotalFeeds()

                when (response.status) {
                    200 -> {
                        _feedList.value = response.data
                    }
                    400 -> {
                        _feedList.value = emptyList()
                    }
                }

                Timber.d("SUCCESS: $response")
            } catch (e: Throwable) {
                Timber.d("FAILED: $e")
            }
        }
    }

    fun getNewFeedPlaceSearchList() {
        viewModelScope.launch {
            try{
                val response = repository.getNewFeedPlaceSearchResult(_placeName)

                when(response.status) {
                    200 -> {
                        _placeList.value = response.placeList
                    }
                    400 -> {
                        _placeList.value = emptyList()
                    }
                }
                Timber.d("SUCCESS GET PLACE LIST: $response")
            } catch (e : Throwable) {
                Timber.d("FAILED: $e")
            }
        }
    }

    fun postNewFeed() {
        viewModelScope.launch {
            try{
                val image = NewFeedInfo.image
                val content = NewFeedInfo.content.toRequestBody("text/plain".toMediaTypeOrNull())
                val placeId = NewFeedInfo.placeId.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                val response = repository.postNewFeed(image,content,placeId)

                when(response.status) {
                    200 -> {
                        _isSuccessPost.value = true
                    }
                    400 -> {
                        _isSuccessPost.value = false
                    }
                }
                Timber.d("SUCCESS POST NEW FEED $response")
            } catch (e : Throwable) {
                Timber.d("FAILED $e")
            }
        }
    }

    fun onClickedPlace(placeName : String, placeId : Long) {
        _placeName = placeName
        this.placeName = _placeName

        NewFeedInfo.placeId = placeId
        NewFeedInfo.placeName = placeName

        _isSelectPlace.value = true
    }

}
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
import okhttp3.RequestBody
import timber.log.Timber

class FeedViewModel(private val repository: FeedRepository) : ViewModel() {

    private var _image: MultipartBody.Part = MultipartBody.Part.createFormData("file", "file")
    var image : MultipartBody.Part = _image
        set(value){
            _image = value
            field = value
        }

    private var _content: String = ""
    var content : String = _content
        set(value){
            _content = value
            field = value
        }

    private var _placeId: Long = 0
    var placeId : Long = _placeId
        set(value){
            _placeId = value
            field = value
        }

    private var _placeName: String = ""
    var placeName : String = _placeName
        set(value){
            _placeName = value
            field = value
        }

    private var _map : HashMap<String, RequestBody> = hashMapOf()
    var map : HashMap<String, RequestBody> = _map
        set(value){
            _map = value
            field = value
        }

    private val _placeList: MutableLiveData<List<NewFeedPlaceSearchResult.Result>> = MutableLiveData<List<NewFeedPlaceSearchResult.Result>>()
    val placeList: LiveData<List<NewFeedPlaceSearchResult.Result>> = _placeList

    private val _isSelectPlace: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val isSelectPlace: LiveData<Boolean> = _isSelectPlace

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
                Timber.d("SUCCESS: $response")
            } catch (e : Throwable) {
                Timber.d("FAILED: $e")
            }
        }
    }

    fun onClickedPlace(placeName : String) {
        _placeName = placeName
        this.placeName = _placeName
        _isSelectPlace.value = true
        Timber.d("PlaceClickEvent! -> $placeName")
    }

}
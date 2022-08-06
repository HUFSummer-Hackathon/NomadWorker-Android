package com.comjeong.nomadworker.ui.feed

import okhttp3.MultipartBody

object NewFeedInfo {
    var image : MultipartBody.Part = MultipartBody.Part.createFormData("file","file")
    var content : String = ""
    var placeId : Long = 0
    var placeName : String = ""
}
package com.comjeong.nomadworker.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserInfo(
    val userId: Long,
    val userProfileImageUrl: String?,
    val userNickname: String?,
    val accessToken: String?,
    val latitude: Float,
    val longitude: Float,
    val isLogin: Boolean
) : Parcelable

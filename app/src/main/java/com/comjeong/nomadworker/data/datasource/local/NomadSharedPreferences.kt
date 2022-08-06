package com.comjeong.nomadworker.data.datasource.local

import android.content.Context
import android.content.SharedPreferences
import com.comjeong.nomadworker.common.Constants
import com.comjeong.nomadworker.model.UserInfo
import timber.log.Timber

object NomadSharedPreferences {
    private const val ACCESS_TOKEN_KEY = "access_token"
    private const val USER_NICKNAME_KEY = "user_nickname"
    private const val USER_LATITUDE_KEY = "user_latitude"
    private const val USER_LONGITUDE_KEY = "user_longitude"
    private const val USER_LOCATION_KEY = "user_location"
    private const val USER_LOGIN_STATUS_KEY = "user_login_status"
    private const val USER_ID_KEY = "user_id"
    private const val USER_PROFILE_IMAGE_KEY = "user_profile_image"

    lateinit var preferences: SharedPreferences

    fun init(context: Context) {
        preferences = context.getSharedPreferences("user", Context.MODE_PRIVATE)
    }

    fun getAccessToken(): String? {
        return preferences.getString(ACCESS_TOKEN_KEY, null)
    }

    fun getUserNickName(): String? {
        return preferences.getString(USER_NICKNAME_KEY, null)
    }

    fun getUserLatitude(): Float {
        return preferences.getFloat(USER_LATITUDE_KEY, 0F)
    }

    fun getUserLongitude(): Float {
        return preferences.getFloat(USER_LONGITUDE_KEY, 0F)
    }

    fun getUserLocation(): String? {
        return preferences.getString(USER_LOCATION_KEY, "서울시청")
    }

    fun getUserIsLogin(): Boolean {
        return preferences.getBoolean(USER_LOGIN_STATUS_KEY, false)
    }

    fun getUserId(): Long {
        return preferences.getLong(USER_ID_KEY, 0)
    }

    fun getUserProfileImage(): String? {
        return preferences.getString(USER_PROFILE_IMAGE_KEY, null)
    }

    private fun setAccessToken(value: String?) {
        preferences.edit().putString(ACCESS_TOKEN_KEY, value).apply()
    }

    private fun setUserNickname(value: String?) {
        preferences.edit().putString(USER_NICKNAME_KEY, value).apply()
    }

    private fun setUserLatitude(value: Float) {
        preferences.edit().putFloat(USER_LATITUDE_KEY, value).apply()
    }

    private fun setUserLongitude(value: Float) {
        preferences.edit().putFloat(USER_LONGITUDE_KEY, value).apply()
    }

    private fun setUserLocation(value : String) {
        preferences.edit().putString(USER_LOCATION_KEY, value).apply()
    }

    private fun setUserIsLogin(value: Boolean) {
        preferences.edit().putBoolean(USER_LOGIN_STATUS_KEY, value).apply()
    }

    private fun setUserId(value: Long) {
        preferences.edit().putLong(USER_ID_KEY, value).apply()
    }

    private fun setUserProfileImage(value: String?) {
        preferences.edit().putString(USER_PROFILE_IMAGE_KEY, value).apply()
    }

    // 로그인
    fun setUser(user: UserInfo) {
        setUserId(user.userId)
        setUserProfileImage(user.userProfileImageUrl)
        setUserNickname(user.userNickname)
        setUserLatitude(user.latitude)
        setUserLongitude(user.longitude)
        setAccessToken(user.accessToken)
        setUserIsLogin(user.isLogin)
    }

    fun logoutUser() {
        setUserId(0)
        setUserProfileImage(null)
        setUserNickname(null)
        setUserLatitude(0.0F)
        setUserLongitude(0.0F)
        setAccessToken(null)
        setUserIsLogin(false)
    }

    // 사용자 위치 갱신
    fun setLocation(latitude : Float, longitude : Float, address : String) {
        setUserLatitude(latitude)
        setUserLongitude(longitude)
        setUserLocation(address)
    }

    // 토큰 삭제
    fun removeToken() {
        setAccessToken(null)
    }

    // 개발 용
    fun loadUserInfo() {
        Timber.d("USER_ID => ${getUserId()}")
        Timber.d("USER_IMAGE => ${getUserProfileImage()}")
        Timber.d("USER_NICKNAME => ${getUserNickName()}")
        Timber.d("USER_TOKEN => ${getAccessToken()}")
        Timber.d("USER_LATITUDE => ${getUserLatitude()}")
        Timber.d("USER_LONGITUDE => ${getUserLongitude()}")
        Timber.d("USER_IS_LOGIN => ${getUserIsLogin()}")
    }
}
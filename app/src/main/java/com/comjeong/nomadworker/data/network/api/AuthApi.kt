package com.comjeong.nomadworker.data.network.api

import com.comjeong.nomadworker.data.model.mypage.ProfileImageResponseData
import com.comjeong.nomadworker.data.model.place.PlaceScrapRequestData
import com.comjeong.nomadworker.data.model.place.PlaceScrapResponseData
import com.comjeong.nomadworker.data.model.settings.PlaceScrapListResponseData
import com.comjeong.nomadworker.data.model.signin.SignInRequestData
import com.comjeong.nomadworker.data.model.signin.SignInResponseData
import com.comjeong.nomadworker.data.model.signup.SignUpRequestData
import com.comjeong.nomadworker.data.model.signup.SignUpResponseData
import com.comjeong.nomadworker.data.model.signup.UserEmailResponseData
import com.comjeong.nomadworker.data.model.signup.UserNicknameResponseData
import okhttp3.MultipartBody
import retrofit2.http.*

interface AuthApi {

    @POST("user")
    suspend fun postSignUp(
        @Body body: SignUpRequestData
    ): SignUpResponseData

    @GET("user/mail")
    suspend fun getUserEmailVerify(
        @Query("userEmail") userEmail: String
    ): UserEmailResponseData

    @GET("user/nickname")
    suspend fun getUserNicknameVerify(
        @Query("userNickname") nickname: String
    ): UserNicknameResponseData

    @POST("user/signin")
    suspend fun postSignIn(
        @Body body: SignInRequestData
    ): SignInResponseData

    @Multipart
    @PUT("user/profile")
    suspend fun updateUserProfileImage(
        @Part profileImage: MultipartBody.Part
    ): ProfileImageResponseData

    @GET("user/placesub")
    suspend fun getPlaceScrapListByUserId(
        @Query("u_id") userId: Long
    ): PlaceScrapListResponseData

    @POST("user/placesub")
    suspend fun postPlaceScrap(
        @Body body: PlaceScrapRequestData
    ): PlaceScrapResponseData
}
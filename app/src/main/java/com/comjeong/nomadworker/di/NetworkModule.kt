package com.comjeong.nomadworker.di

import com.comjeong.nomadworker.common.Constants.BASE_URL
import com.comjeong.nomadworker.data.network.NetworkInterceptor
import com.comjeong.nomadworker.data.network.api.AuthApi
import com.comjeong.nomadworker.data.network.api.FeedApi
import com.comjeong.nomadworker.data.network.api.PlaceApi
import com.comjeong.nomadworker.data.network.api.ReplyApi
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
            .addNetworkInterceptor(NetworkInterceptor())
            .addInterceptor(Interceptor { chain ->
                chain.proceed(
                    chain.request().newBuilder().build()
                )
            })
            .build()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .client(get())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .baseUrl(BASE_URL)
            .build()
    }

    single<AuthApi> {
        get<Retrofit>().create(AuthApi::class.java)
    }

    single<PlaceApi> {
        get<Retrofit>().create(PlaceApi::class.java)
    }

    single<FeedApi> {
        get<Retrofit>().create(FeedApi::class.java)
    }

    single<ReplyApi> {
        get<Retrofit>().create(ReplyApi::class.java)
    }
}
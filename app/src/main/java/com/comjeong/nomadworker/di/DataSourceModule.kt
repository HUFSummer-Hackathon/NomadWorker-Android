package com.comjeong.nomadworker.di

import com.comjeong.nomadworker.data.datasource.local.SettingsLocalDataSourceImpl
import com.comjeong.nomadworker.data.datasource.remote.mypage.MyPageRemoteDataSourceImpl
import com.comjeong.nomadworker.data.datasource.remote.feed.FeedsRemoteDataSourceImpl
import com.comjeong.nomadworker.data.datasource.remote.home.HomeRemoteDataSourceImpl
import com.comjeong.nomadworker.data.datasource.remote.place.PlaceDetailRemoteDataSourceImpl
import com.comjeong.nomadworker.data.datasource.remote.place.PlaceRegionRemoteDataSourceImpl
import com.comjeong.nomadworker.data.datasource.remote.search.PlaceSearchDataSourceImpl
import com.comjeong.nomadworker.data.datasource.remote.settings.SettingsRemoteDataSourceImpl
import com.comjeong.nomadworker.data.datasource.remote.signin.SignInRemoteDataSourceImpl
import com.comjeong.nomadworker.data.datasource.remote.signup.SignUpRemoteDataSourceImpl
import com.comjeong.nomadworker.data.datasource.source.SignInDataSource
import com.comjeong.nomadworker.data.datasource.source.SignUpDataSource
import com.comjeong.nomadworker.data.datasource.source.feed.FeedsDataSource
import com.comjeong.nomadworker.data.datasource.source.home.HomeRemoteDataSource
import com.comjeong.nomadworker.data.datasource.source.mypage.MyPageDataSource
import com.comjeong.nomadworker.data.datasource.source.place.PlaceDetailDataSource
import com.comjeong.nomadworker.data.datasource.source.place.PlaceRegionDataSource
import com.comjeong.nomadworker.data.datasource.source.search.PlaceSearchDataSource
import com.comjeong.nomadworker.data.datasource.source.settings.SettingsLocalDataSource
import com.comjeong.nomadworker.data.datasource.source.settings.SettingsRemoteDataSource
import org.koin.dsl.module

val dataSourceModule = module {
    single<SignUpDataSource> { SignUpRemoteDataSourceImpl(get()) }
    single<SignInDataSource> { SignInRemoteDataSourceImpl(get()) }
    single<HomeRemoteDataSource> { HomeRemoteDataSourceImpl(get()) }
    single<PlaceRegionDataSource> { PlaceRegionRemoteDataSourceImpl(get()) }
    single<PlaceDetailDataSource> { PlaceDetailRemoteDataSourceImpl(get()) }
    single<FeedsDataSource> { FeedsRemoteDataSourceImpl(get()) }
    single<MyPageDataSource> { MyPageRemoteDataSourceImpl(get(), get()) }
    single<PlaceSearchDataSource> { PlaceSearchDataSourceImpl(get()) }
    single<SettingsLocalDataSource> { SettingsLocalDataSourceImpl() }
    single<SettingsRemoteDataSource> { SettingsRemoteDataSourceImpl(get()) }
}
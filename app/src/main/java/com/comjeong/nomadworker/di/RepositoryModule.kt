package com.comjeong.nomadworker.di

import com.comjeong.nomadworker.data.repository.feed.FeedRepositoryImpl
import com.comjeong.nomadworker.data.repository.home.HomeRepositoryImpl
import com.comjeong.nomadworker.data.repository.mypage.MyPageRepositoryImpl
import com.comjeong.nomadworker.data.repository.place.PlaceDetailRepositoryImpl
import com.comjeong.nomadworker.data.repository.place.PlaceRegionRepositoryImpl
import com.comjeong.nomadworker.data.repository.reply.ReplyRepositoryImpl
import com.comjeong.nomadworker.data.repository.search.PlaceSearchRepositoryImpl
import com.comjeong.nomadworker.data.repository.settings.SettingsRepositoryImpl
import com.comjeong.nomadworker.data.repository.signin.SignInRepositoryImpl
import com.comjeong.nomadworker.data.repository.signup.SignUpRepositoryImpl
import com.comjeong.nomadworker.domain.repository.feed.FeedRepository
import com.comjeong.nomadworker.domain.repository.home.HomeRepository
import com.comjeong.nomadworker.domain.repository.mypage.MyPageRepository
import com.comjeong.nomadworker.domain.repository.place.PlaceDetailRepository
import com.comjeong.nomadworker.domain.repository.place.PlaceRegionRepository
import com.comjeong.nomadworker.domain.repository.reply.ReplyRepository
import com.comjeong.nomadworker.domain.repository.search.PlaceSearchRepository
import com.comjeong.nomadworker.domain.repository.settings.SettingsRepository
import com.comjeong.nomadworker.domain.repository.signin.SignInRepository
import com.comjeong.nomadworker.domain.repository.signup.SignUpRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<SignUpRepository> { SignUpRepositoryImpl(get()) }
    single<SignInRepository> { SignInRepositoryImpl(get()) }
    single<HomeRepository> { HomeRepositoryImpl(get()) }
    single<PlaceRegionRepository> { PlaceRegionRepositoryImpl(get()) }
    single<PlaceDetailRepository> { PlaceDetailRepositoryImpl(get()) }
    single<FeedRepository> { FeedRepositoryImpl(get()) }
    single<MyPageRepository> { MyPageRepositoryImpl(get()) }
    single<PlaceSearchRepository> { PlaceSearchRepositoryImpl(get()) }
    single<SettingsRepository> { SettingsRepositoryImpl(get()) }
    single<ReplyRepository> { ReplyRepositoryImpl(get()) }
}
package com.comjeong.nomadworker.di

import com.comjeong.nomadworker.ui.feed.FeedViewModel
import com.comjeong.nomadworker.ui.home.HomeViewModel
import com.comjeong.nomadworker.ui.mypage.MyPageViewModel
import com.comjeong.nomadworker.ui.place.PlaceDetailViewModel
import com.comjeong.nomadworker.ui.place.PlaceRegionViewModel
import com.comjeong.nomadworker.ui.reply.ReplyViewModel
import com.comjeong.nomadworker.ui.search.SearchViewModel
import com.comjeong.nomadworker.ui.setting.SettingsViewModel
import com.comjeong.nomadworker.ui.signin.SignInViewModel
import com.comjeong.nomadworker.ui.signup.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SignUpViewModel(get()) }
    viewModel { SignInViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { PlaceRegionViewModel(get()) }
    viewModel { PlaceDetailViewModel(get()) }
    viewModel { FeedViewModel(get()) }
    viewModel { MyPageViewModel(get(), get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { SettingsViewModel(get()) }
    viewModel { ReplyViewModel(get()) }
}
package com.examle.effectivecourses.di.module

import com.examle.effectivecourses.ui.home.HomeViewModel
import com.examle.effectivecourses.ui.favorite.FavoriteViewModel
import com.examle.effectivecourses.ui.login.LoginViewModel
import com.examle.effectivecourses.ui.profile.ProfileViewModel
import com.examle.effectivecourses.ui.detail.DetailViewModel
import com.examle.effectivecourses.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::FavoriteViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::ProfileViewModel)
    viewModelOf(::DetailViewModel)
    viewModelOf(::MainViewModel)
}
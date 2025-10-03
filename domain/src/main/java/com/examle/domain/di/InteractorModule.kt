package com.examle.domain.di

import android.content.Context
import com.examle.domain.interactor.CourseDetailInteractor
import com.examle.domain.interactor.CourseInteractor
import com.examle.domain.interactor.FavoriteInteractor
import com.examle.domain.interactor.LoginInteractor
import com.examle.domain.interactor.MyCoursesInteractor
import org.koin.dsl.module

val interactorModule = module {
    single { CourseInteractor(get(), get()) }
    single { LoginInteractor(get()) }
    single { CourseDetailInteractor(get(), get()) }
    single { MyCoursesInteractor(get(), get()) }
    single { FavoriteInteractor(get()) }
}
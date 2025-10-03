package com.examle.data.di

import com.examle.data.repository.CoursesRepositoryImpl
import com.examle.data.repository.FavoriteRepositoryImpl
import com.examle.data.repository.LoginRepositoryImpl
import com.examle.data.repository.MyCoursesRepositoryImpl
import com.examle.domain.repository.CoursesRepository
import com.examle.domain.repository.FavoriteRepository
import com.examle.domain.repository.LoginRepository
import com.examle.domain.repository.MyCoursesRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<CoursesRepository> { CoursesRepositoryImpl(get(), get()) }
    single<FavoriteRepository> { FavoriteRepositoryImpl(get()) }
    single<LoginRepository> { LoginRepositoryImpl(get()) }
    single<MyCoursesRepository> { MyCoursesRepositoryImpl(get()) }
}
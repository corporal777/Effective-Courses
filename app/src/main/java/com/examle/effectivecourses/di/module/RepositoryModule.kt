package com.examle.effectivecourses.di.module

import com.examle.effectivecourses.di.repository.CoursesRepository
import com.examle.effectivecourses.di.repository.CoursesRepositoryImpl
import com.examle.effectivecourses.di.repository.FavoriteRepository
import com.examle.effectivecourses.di.repository.FavoriteRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<CoursesRepository> { CoursesRepositoryImpl(get(), get()) }
    single<FavoriteRepository> { FavoriteRepositoryImpl(get()) }
}
package com.examle.data.di

import com.examle.data.data.AppData
import com.examle.domain.repository.AppPrefs
import com.examle.data.repository.AppPrefsImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appDataModule = module {

    fun provideAppData(appPrefs: AppPrefs): AppData = AppData(appPrefs)
    single<AppPrefs> { AppPrefsImpl(androidContext()) }
    single { provideAppData(get()) }
}
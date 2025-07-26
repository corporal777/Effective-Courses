package com.examle.effectivecourses.di.module

import com.examle.effectivecourses.dataSource.data.AppData
import com.examle.effectivecourses.dataSource.data.AppPrefs
import com.examle.effectivecourses.dataSource.data.AppPrefsImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appDataModule = module {

    fun provideAppData(appPrefs: AppPrefs): AppData = AppData(appPrefs)
    single<AppPrefs> { AppPrefsImpl(androidContext()) }
    single { provideAppData(get()) }
}
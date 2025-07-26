package com.examle.effectivecourses

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.examle.effectivecourses.di.module.appDataModule
import com.examle.effectivecourses.di.module.dataBaseModule
import com.examle.effectivecourses.di.module.remoteDataSourceModule
import com.examle.effectivecourses.di.module.repositoryModule
import com.examle.effectivecourses.di.module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(applicationContext)

            modules(appDataModule, repositoryModule, remoteDataSourceModule, viewModelModule, dataBaseModule)
        }
    }
}
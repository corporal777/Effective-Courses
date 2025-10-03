package com.examle.effectivecourses

import android.app.Application
import com.examle.data.di.appDataModule
import com.examle.data.di.dataBaseModule
import com.examle.data.di.remoteDataSourceModule
import com.examle.data.di.repositoryModule
import com.examle.domain.di.interactorModule
import com.examle.effectivecourses.di.module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(applicationContext)

            modules(
                appDataModule,
                repositoryModule,
                remoteDataSourceModule,
                viewModelModule,
                dataBaseModule,
                interactorModule
            )
        }
    }
}
package com.examle.effectivecourses.di.module

import android.content.Context
import com.effective.localdatamodule.RoomDb
import com.examle.effectivecourses.dataSource.CourseDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataBaseModule = module {

    fun provideDb(context: Context): RoomDb {
        return RoomDb.getInstance(context)
    }

    fun provideDataSource(db: RoomDb, context: Context): CourseDataSource {
        return CourseDataSource(db.courseDao(), context)
    }
    single { provideDb(androidContext()) }
    single { provideDataSource(get(), androidContext()) }
}
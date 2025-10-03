package com.examle.data.di

import android.content.Context
import com.examle.data.db.CourseDataSource
import com.examle.data.db.RoomDb
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataBaseModule = module {
    fun provideDb(context: Context): RoomDb = RoomDb.getInstance(context)
    fun provideDataSource(db: RoomDb): CourseDataSource = CourseDataSource(db.courseDao())

    single { provideDb(androidContext()) }
    single { provideDataSource(get()) }
}
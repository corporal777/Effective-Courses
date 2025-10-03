package com.examle.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.examle.data.db.dao.CourseDao
import com.examle.data.db.dbo.CourseDbo

@Database(entities = [CourseDbo::class], version = 1)
abstract class RoomDb: RoomDatabase() {

    abstract fun courseDao(): CourseDao

    companion object {
        @Volatile
        private var INSTANCE: RoomDb? = null

        fun getInstance(context: Context): RoomDb =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also {
                    INSTANCE = it
                }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                RoomDb::class.java, "effective.db")
                .fallbackToDestructiveMigration()
                .build()
    }
}
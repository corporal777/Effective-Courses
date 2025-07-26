package com.effective.localdatamodule

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.effective.localdatamodule.dao.CourseDao
import com.effective.localdatamodule.dbo.CourseDbo

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
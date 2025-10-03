package com.examle.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.examle.data.db.dbo.CourseDbo
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(course: CourseDbo) : Long

    @Query("SELECT * FROM courses")
    suspend fun getAll(): List<CourseDbo>

    @Query("SELECT * FROM courses WHERE id=:courseId ")
    suspend fun getById(courseId: String) : CourseDbo?

    @Query("DELETE FROM courses WHERE id = :courseId")
    suspend fun deleteById(courseId: String) : Int
}
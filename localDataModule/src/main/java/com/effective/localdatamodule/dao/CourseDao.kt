package com.effective.localdatamodule.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.effective.localdatamodule.dbo.CourseDbo
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface CourseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(course: CourseDbo) : Long

    @Query("SELECT * FROM courses")
    fun getAll(): Maybe<List<CourseDbo>>

    @Query("SELECT * FROM courses WHERE id=:courseId ")
    fun getById(courseId: String) : CourseDbo?

    @Query("SELECT * FROM courses")
    fun getAllAsFlowable(): Flowable<List<CourseDbo>>

    @Query("DELETE FROM courses WHERE id = :courseId")
    fun deleteById(courseId: String) : Int
}
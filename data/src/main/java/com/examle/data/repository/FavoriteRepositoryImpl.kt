package com.examle.data.repository

import android.util.Log
import com.examle.common.exceptions.RoomDbException
import com.examle.data.db.CourseDataSource
import com.examle.data.mapper.mapToCourseModelFromDbo
import com.examle.domain.model.CourseFavoriteModel
import com.examle.domain.model.CourseModel
import com.examle.domain.repository.FavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavoriteRepositoryImpl(private val dataSource: CourseDataSource) :
    FavoriteRepository {

    override suspend fun getFavoriteCourses(): List<CourseModel> {
        return withContext(Dispatchers.IO) {
            dataSource.getFavoriteCourses().map { it.mapToCourseModelFromDbo() }
        }
    }

    override suspend fun addCourseFavorite(model: CourseModel): CourseFavoriteModel {
        val result = dataSource.addCourse(model)
        return if (result) CourseFavoriteModel(model.id, true)
        else throw RoomDbException()

    }

    override suspend fun removeCourseFavorite(model: CourseModel): CourseFavoriteModel {
        val result = dataSource.deleteCourse(model.id)
        return if (result) CourseFavoriteModel(model.id, false)
        else throw RoomDbException()
    }
}
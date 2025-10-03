package com.examle.domain.repository

import com.examle.domain.model.CourseFavoriteModel
import com.examle.domain.model.CourseModel

interface CoursesRepository {
    suspend fun getCourses(): List<CourseModel>
    suspend fun getCourseById(id : String) : CourseModel?
}
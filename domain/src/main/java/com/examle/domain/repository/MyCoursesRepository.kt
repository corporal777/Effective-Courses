package com.examle.domain.repository

import com.examle.domain.model.MyCourseModel

interface MyCoursesRepository {
    suspend fun getMyCourses() : List<MyCourseModel>
}
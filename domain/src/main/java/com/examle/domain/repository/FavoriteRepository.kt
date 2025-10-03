package com.examle.domain.repository

import com.examle.domain.model.CourseFavoriteModel
import com.examle.domain.model.CourseModel

interface FavoriteRepository {

    suspend fun getFavoriteCourses() : List<CourseModel>
    suspend fun addCourseFavorite(model: CourseModel) : CourseFavoriteModel
    suspend fun removeCourseFavorite(model: CourseModel) : CourseFavoriteModel
}
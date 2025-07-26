package com.examle.effectivecourses.di.repository

import com.examle.effectivecourses.dataSource.model.CourseFavoriteModel
import com.effective.networkmodule.model.CourseModel
import io.reactivex.Maybe
import io.reactivex.Single

interface CoursesRepository {
    fun getCourses() : Maybe<List<CourseModel>>
    fun getCourseById(id : String) : Single<CourseModel>
    fun addCourseFavorite(model: CourseModel) : Maybe<CourseFavoriteModel>
    fun removeCourseFavorite(model: CourseModel) : Maybe<CourseFavoriteModel>
}
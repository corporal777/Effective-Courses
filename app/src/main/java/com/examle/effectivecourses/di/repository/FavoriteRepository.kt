package com.examle.effectivecourses.di.repository

import com.examle.effectivecourses.dataSource.model.CourseFavoriteModel
import com.effective.networkmodule.model.CourseModel
import io.reactivex.Maybe

interface FavoriteRepository {

    fun getFavoriteCourses() : Maybe<List<CourseModel>>
    fun removeCourseFavorite(model: CourseModel) : Maybe<CourseFavoriteModel>
}
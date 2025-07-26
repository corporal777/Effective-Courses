package com.examle.effectivecourses.di.repository

import com.effective.networkmodule.api.ApiService
import com.examle.effectivecourses.R
import com.examle.effectivecourses.dataSource.CourseDataSource
import com.examle.effectivecourses.dataSource.model.CourseFavoriteModel
import com.effective.networkmodule.model.CourseModel
import com.examle.effectivecourses.exceptions.RoomDbException
import io.reactivex.Maybe
import io.reactivex.Single

class CoursesRepositoryImpl(
    private val apiService: ApiService,
    private val dataSource: CourseDataSource
) : CoursesRepository {


    override fun getCourseById(id: String): Single<CourseModel> {
        return apiService.getCourses().flatMapSingle { courses ->
            val local = dataSource.getCourseById(id)
            val remote = courses.courses.find { x -> x.id == id }
            val newItem = remote?.copy(hasLike = local != null)

            if (newItem == null) Single.error(NullPointerException())
            else Single.just(newItem)
        }
    }

    override fun getCourses(): Maybe<List<CourseModel>> {
        return dataSource.getFavoriteCourses()
            .flatMap { local ->
                apiService.getCourses().map {
                    it.courses.map { model ->
                        val hasLike = if (local.isEmpty()) false else local.any { it.id == model.id }
                        model.copy(hasLike = hasLike)
                    }
                }
            }
    }


    override fun addCourseFavorite(model: CourseModel): Maybe<CourseFavoriteModel> {
        return dataSource.addCourse(model).flatMap {
            if (it) Maybe.just(CourseFavoriteModel(model.id, true))
            else Maybe.error(RoomDbException())
        }
    }

    override fun removeCourseFavorite(model: CourseModel): Maybe<CourseFavoriteModel> {
        return dataSource.deleteCourse(model.id).flatMap {
            if (it) Maybe.just(CourseFavoriteModel(model.id, false))
            else Maybe.error(RoomDbException())
        }
    }
}
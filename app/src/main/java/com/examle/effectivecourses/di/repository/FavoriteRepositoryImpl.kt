package com.examle.effectivecourses.di.repository

import com.examle.effectivecourses.dataSource.model.CourseFavoriteModel
import com.effective.networkmodule.model.CourseModel
import com.examle.effectivecourses.dataSource.CourseDataSource
import com.examle.effectivecourses.exceptions.RoomDbException
import io.reactivex.Maybe

class FavoriteRepositoryImpl(private val dataSource: CourseDataSource) : FavoriteRepository {

    override fun getFavoriteCourses(): Maybe<List<CourseModel>> {
        return dataSource.getFavoriteCourses()
            .map {
                it.map { dbo ->
                    CourseModel(
                        dbo.id,
                        dbo.title,
                        dbo.text,
                        dbo.price,
                        dbo.rate,
                        dbo.startDate,
                        true,
                        dbo.publishDate
                    )
                }
            }
    }

    override fun removeCourseFavorite(model: CourseModel): Maybe<CourseFavoriteModel> {
        return dataSource.deleteCourse(model.id).flatMap {
            if (it) Maybe.just(CourseFavoriteModel(model.id, false))
            else Maybe.error(RoomDbException())
        }
    }
}
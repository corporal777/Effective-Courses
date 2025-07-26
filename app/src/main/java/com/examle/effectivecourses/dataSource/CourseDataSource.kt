package com.examle.effectivecourses.dataSource

import android.content.Context
import com.effective.localdatamodule.dao.CourseDao
import com.effective.localdatamodule.dbo.CourseDbo
import com.examle.effectivecourses.R
import com.effective.networkmodule.model.CourseModel
import io.reactivex.Maybe
import io.reactivex.Single

class CourseDataSource(
    private val courseDao: CourseDao,
    private val context: Context
) {

    fun addCourse(model: CourseModel): Maybe<Boolean> {
        return Maybe.defer {
            val dbo = CourseDbo(
                model.id, model.title, model.text,
                model.price, model.rate, model.startDate, model.publishDate
            )
            Maybe.just(courseDao.insert(dbo) > 0)
        }
    }

    fun deleteCourse(id : String) : Maybe<Boolean> {
        return Maybe.defer { Maybe.just(courseDao.deleteById(id) > 0) }
    }

    fun getFavoriteCourses() = courseDao.getAll()

    fun getCourseById(id : String) : CourseDbo?{
        return courseDao.getById(id)
    }
}
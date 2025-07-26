package com.examle.effectivecourses.dataSource.data

import com.effective.networkmodule.model.CourseModel
import io.reactivex.subjects.PublishSubject

class AppData(private val appPrefs: AppPrefs) {

    var isLoggedIn: Boolean = appPrefs.isLoggedIn
        set(value) {
            if (field == value) return
            field = value
            appPrefs.isLoggedIn = value
        }



    private val courseSubject = PublishSubject.create<CourseModel>()

    fun setCourseChanged(model : CourseModel) = courseSubject.onNext(model)
    fun getCourseChangedSubject(): PublishSubject<CourseModel> = courseSubject
}
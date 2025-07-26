package com.examle.effectivecourses.ui.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.effective.networkmodule.model.CourseModel
import com.examle.effectivecourses.dataSource.data.AppData
import com.examle.effectivecourses.di.repository.CoursesRepository
import com.examle.effectivecourses.extensions.call
import com.examle.effectivecourses.extensions.performOnBackgroundOutOnMain
import com.examle.effectivecourses.ui.base.BaseViewModel
import io.reactivex.Maybe
import io.reactivex.rxkotlin.subscribeBy

class DetailViewModel(
    private val repository: CoursesRepository,
    private val appData: AppData
) : BaseViewModel() {

    private val _courseDetail = mutableStateOf<CourseModel?>(null)
    val courseDetail: State<CourseModel?> = _courseDetail


    fun getCourseDetail(id: String) {
        repository.getCourseById(id)
            .performOnBackgroundOutOnMain()
            .subscribeBy(
                onError = { it.printStackTrace() },
                onSuccess = { _courseDetail.value = it }
            ).call(compositeDisposable)
    }


    fun addCourseFavorite(model: CourseModel) {
        Maybe.defer {
            if (model.hasLike) repository.removeCourseFavorite(model)
            else repository.addCourseFavorite(model)
        }
            .map { model.copy(hasLike = it.isFavorite) }
            .doOnSuccess { appData.setCourseChanged(it) }
            .performOnBackgroundOutOnMain()
            .subscribeBy(
                onError = { it.printStackTrace() },
                onSuccess = { _courseDetail.value = it }
            ).call(compositeDisposable)
    }

}
package com.examle.effectivecourses.ui.favorite

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.effective.networkmodule.model.CourseModel
import com.examle.effectivecourses.dataSource.data.AppData
import com.examle.effectivecourses.di.repository.FavoriteRepository
import com.examle.effectivecourses.extensions.call
import com.examle.effectivecourses.extensions.performOnBackgroundOutOnMain
import com.examle.effectivecourses.extensions.withDelay
import com.examle.effectivecourses.ui.base.BaseViewModel
import io.reactivex.rxkotlin.subscribeBy

class FavoriteViewModel(
    private val repository: FavoriteRepository,
    private val appData: AppData
) : BaseViewModel() {

    private val _courses = mutableStateOf<List<CourseModel?>>(List(2) { null })
    val courses: State<List<CourseModel?>> = _courses

    init {
        repository.getFavoriteCourses()
            .withDelay(300)
            .performOnBackgroundOutOnMain()
            .subscribeBy(
                onError = { it.printStackTrace() },
                onSuccess = { _courses.value = it }
            ).call(compositeDisposable)
    }

    fun removeFavoriteCourse(model: CourseModel) {
        repository.removeCourseFavorite(model)
            .doOnSuccess { appData.setCourseChanged(model.copy(hasLike = it.isFavorite)) }
            .flatMap { repository.getFavoriteCourses() }
            .withDelay(300)
            .performOnBackgroundOutOnMain()
            .withButtonLoading(model.id)
            .subscribeBy(
                onError = { it.printStackTrace() },
                onSuccess = { _courses.value = it }
            ).call(compositeDisposable)
    }


}
package com.examle.effectivecourses.ui.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.effective.networkmodule.model.CourseModel
import com.examle.effectivecourses.dataSource.data.AppData
import com.examle.effectivecourses.di.repository.CoursesRepository
import com.examle.effectivecourses.extensions.call
import com.examle.effectivecourses.extensions.performOnBackgroundOutOnMain
import com.examle.effectivecourses.ui.base.BaseViewModel
import io.reactivex.Maybe
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMap
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach

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
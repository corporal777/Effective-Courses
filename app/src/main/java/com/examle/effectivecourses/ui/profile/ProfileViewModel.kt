package com.examle.effectivecourses.ui.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.examle.effectivecourses.dataSource.data.AppData
import com.examle.effectivecourses.dataSource.model.MyCourseModel
import com.examle.effectivecourses.di.repository.CoursesRepository
import com.examle.effectivecourses.extensions.call
import com.examle.effectivecourses.extensions.performOnBackgroundOutOnMain
import com.examle.effectivecourses.extensions.withDelay
import com.examle.effectivecourses.ui.base.BaseViewModel
import io.reactivex.Completable
import io.reactivex.rxkotlin.subscribeBy

class ProfileViewModel(
    private val repository: CoursesRepository,
    private val appData: AppData
) : BaseViewModel() {

    private val _courses = mutableStateOf<List<MyCourseModel?>>(List(1) { null })
    val courses: State<List<MyCourseModel?>> = _courses

    var onLogoutSuccess: () -> Unit = { }

    init {
        repository.getCourses()
            .map { it.subList(0, 2).map { MyCourseModel.createFromRemote(it) } }
            .performOnBackgroundOutOnMain()
            .subscribeBy { _courses.value = it }
            .call(compositeDisposable)
    }

    fun logoutProfile() {
        Completable.fromAction { appData.isLoggedIn = false }
            .withDelay(1000)
            .performOnBackgroundOutOnMain()
            .withLoading()
            .subscribeBy { onLogoutSuccess.invoke() }
            .call(compositeDisposable)
    }
}
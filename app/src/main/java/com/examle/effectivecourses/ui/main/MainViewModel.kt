package com.examle.effectivecourses.ui.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.effective.networkmodule.model.CourseModel
import com.examle.effectivecourses.dataSource.data.AppData
import com.examle.effectivecourses.extensions.call
import com.examle.effectivecourses.extensions.performOnBackgroundOutOnMain
import com.examle.effectivecourses.extensions.withDelay
import com.examle.effectivecourses.ui.base.BaseViewModel
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.rxkotlin.subscribeBy

class MainViewModel(private val appData: AppData) : BaseViewModel() {

    private val _isSplashShown = mutableStateOf<Boolean>(true)
    val isSplashShown: State<Boolean> = _isSplashShown

    init {
        Completable.complete()
            .withDelay(1000)
            .performOnBackgroundOutOnMain()
            .subscribeBy {
                _isSplashShown.value = false
            }.call(compositeDisposable)
    }
}
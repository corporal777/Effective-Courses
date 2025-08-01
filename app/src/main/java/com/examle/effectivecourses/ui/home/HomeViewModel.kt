package com.examle.effectivecourses.ui.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.effective.networkmodule.model.CourseModel
import com.examle.effectivecourses.dataSource.data.AppData
import com.examle.effectivecourses.di.repository.CoursesRepository
import com.examle.effectivecourses.extensions.call
import com.examle.effectivecourses.extensions.performOnBackgroundOutOnMain
import com.examle.effectivecourses.extensions.withDelay
import com.examle.effectivecourses.ui.base.BaseViewModel
import com.examle.effectivecourses.utils.DateUtils.timeMillis
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.rxkotlin.subscribeBy

class HomeViewModel(
    private val repository: CoursesRepository,
    private val appData: AppData
) : BaseViewModel() {

    private val _courses = mutableStateOf<List<CourseModel?>>(List(2) { null })
    val courses: State<List<CourseModel?>> = _courses

    private val _shimmerLoading = mutableStateOf<Boolean>(true)
    val shimmerLoading: State<Boolean> = _shimmerLoading

//    private val _uiState = MutableStateFlow<List<CourseModel?>>(List(3) { null })
//    val uiState: StateFlow<List<CourseModel?>> = _uiState.asStateFlow()


    private val coursesList = arrayListOf<CourseModel>()
    private var searchText = ""
    var itemNum = "0"


    init {
        repository.getCourses()
            .doOnSuccess { coursesList.addAll(it) }
            .performOnBackgroundOutOnMain()
            .subscribeBy { list -> _courses.value = list }
            .call(compositeDisposable)

        appData.getCourseChangedSubject()
            .map { course ->
                val item = coursesList.find { it.id == course.id }
                val index = coursesList.indexOf(item)
                item?.let { coursesList[index] = it.copy(hasLike = course.hasLike) }
                coursesList.map { it }
            }
            .performOnBackgroundOutOnMain()
            .subscribeBy(
                onError = { it.printStackTrace() },
                onNext = { _courses.value = it }
            ).call(compositeDisposable)

    }

    fun sortCoursesByDate(isSorted: Boolean) {
        showShimmer()
        Maybe.just(coursesList)
            .map { if (isSorted) it.sortedByDescending { it.publishDate.timeMillis() } else it }
            .withDelay(500)
            .performOnBackgroundOutOnMain()
            .subscribeBy(
                onError = { it.printStackTrace() },
                onSuccess = { _courses.value = it }
            ).call(compositeDisposable)
    }

    fun searchCourse(text: String) {
        if (searchText == text) return
        searchText = text
        //showShimmer()
        Flowable.just(coursesList)
            .map { it.filter { if (text.isNotBlank()) it.text.contains(text, true) else true } }
            //.withDelay(500)
            .performOnBackgroundOutOnMain()
            .subscribeBy(
                onError = { it.printStackTrace() },
                onNext = { _courses.value = it }
            ).call(compositeDisposable)
    }

    fun addCourseToFavorite(model: CourseModel) {
        Maybe.defer {
            if (model.hasLike) repository.removeCourseFavorite(model)
            else repository.addCourseFavorite(model)
        }
            .doOnSuccess { course ->
                val item = coursesList.find { it.id == course.id }
                val index = coursesList.indexOf(item)
                item?.let { coursesList.set(index, it.copy(hasLike = course.isFavorite)) }
            }
            .map { coursesList.map { it } }
            .withDelay(500)
            .performOnBackgroundOutOnMain()
            .withButtonLoading(model.id)
            .subscribeBy(
                onError = { it.printStackTrace() },
                onSuccess = { _courses.value = it }
            ).call(compositeDisposable)
    }


    private fun showShimmer() {
        _courses.value = List(2) { null }
    }
}
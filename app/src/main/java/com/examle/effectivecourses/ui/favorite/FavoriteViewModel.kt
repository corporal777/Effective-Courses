package com.examle.effectivecourses.ui.favorite

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.examle.domain.model.CourseModel
import com.examle.domain.repository.FavoriteRepository
import com.examle.data.data.AppData
import com.examle.domain.interactor.FavoriteInteractor
import com.examle.domain.model.DataState
import com.examle.effectivecourses.ui.base.BaseViewModel
import com.examle.effectivecourses.ui.base.UIData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FavoriteViewModel(
    uiData: UIData,
    private val interactor: FavoriteInteractor
) : BaseViewModel(uiData) {

    private val _courses = MutableStateFlow<DataState<List<CourseModel>>>(DataState.Loading)
    val courses: StateFlow<DataState<List<CourseModel>>> = _courses.asStateFlow()

    init {
        getFavoriteCourses(true)
    }

    private fun getFavoriteCourses(withDelay : Boolean) {
        viewModelScope.launch {
            if (withDelay) delay(1000)
            interactor.getFavoriteCourses()
                .fold(
                    onSuccess = { _courses.emit(DataState.Success(it)) },
                    onFailure = { _courses.emit(DataState.Error) }
                )
        }
    }

    fun removeFavoriteCourse(model: CourseModel) {
        viewModelScope.launch {
            interactor.removeCourseFavourite(model)
                .withProgressLoading()
                .catch { it.printStackTrace() }
                .collectLatest {
                    getFavoriteCourses(false)
                }
        }
    }
}
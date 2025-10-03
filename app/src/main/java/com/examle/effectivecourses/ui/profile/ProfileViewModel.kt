package com.examle.effectivecourses.ui.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.examle.data.data.AppData
import com.examle.domain.interactor.MyCoursesInteractor
import com.examle.domain.model.DataState
import com.examle.domain.model.MyCourseModel
import com.examle.domain.repository.CoursesRepository
import com.examle.effectivecourses.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val interactor: MyCoursesInteractor
) : BaseViewModel() {

    private val _courses = MutableStateFlow<DataState<List<MyCourseModel>>>(DataState.Loading)
    val courses: StateFlow<DataState<List<MyCourseModel>>> = _courses.asStateFlow()

    private val _logoutSuccess = MutableStateFlow<Boolean>(false)
    val logoutSuccess: StateFlow<Boolean> = _logoutSuccess.asStateFlow()


    init {
        viewModelScope.launch {
            interactor.getMyCourses()
                .fold(
                    onSuccess = { _courses.emit(DataState.Success(it)) },
                    onFailure = { _courses.emit(DataState.Error) }
                )
        }
    }

    fun logoutProfile() {
        viewModelScope.launch {
            interactor.logoutFromProfile()
                .withProgressLoading()
                .catch { e -> e.printStackTrace() }
                .collect { _logoutSuccess.update { true } }
        }
    }
}
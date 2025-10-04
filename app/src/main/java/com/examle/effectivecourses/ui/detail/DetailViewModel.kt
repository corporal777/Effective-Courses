package com.examle.effectivecourses.ui.detail

import androidx.lifecycle.viewModelScope
import com.examle.domain.interactor.CourseDetailInteractor
import com.examle.domain.model.CourseModel
import com.examle.domain.model.DataState
import com.examle.effectivecourses.ui.base.BaseViewModel
import com.examle.effectivecourses.ui.base.UIData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DetailViewModel(
    uiData: UIData,
    private val interactor: CourseDetailInteractor
) : BaseViewModel(uiData) {

    private val _courseDetail = MutableStateFlow<DataState<CourseModel>>(DataState.Loading)
    val courseDetail: StateFlow<DataState<CourseModel>> = _courseDetail.asStateFlow()


    fun getCourseDetail(id: String) {
        viewModelScope.launch {
            interactor.getCourseById(id)
                .fold(
                    onSuccess = { _courseDetail.emit(DataState.Success(it)) },
                    onFailure = { _courseDetail.emit(DataState.Error) }
                )
        }
    }


    fun addCourseFavorite(model: CourseModel) {
        viewModelScope.launch {
            interactor.addOrRemoveCourseFavourite(model)
                .withProgressLoading()
                .catch { it.printStackTrace() }
                .collectLatest {
                    _courseDetail.emit(DataState.Success(it))
                }
        }
    }

}
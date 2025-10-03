package com.examle.effectivecourses.ui.home

import androidx.lifecycle.viewModelScope
import com.examle.domain.interactor.CourseInteractor
import com.examle.domain.model.CourseModel
import com.examle.domain.model.DataState
import com.examle.effectivecourses.ui.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val interactor: CourseInteractor
) : BaseViewModel() {

    private val refreshListener = MutableSharedFlow<StateTriggers>()

    val courses: Flow<DataState<List<CourseModel>>> = flow {
        emit(interactor.getCoursesList()
            .fold(onSuccess = { DataState.Success(it) }, onFailure = { DataState.Error })
        )
        refreshListener.collect { refreshParams ->
            when (refreshParams) {
                is StateTriggers.Loading -> emit(DataState.Loading)
                is StateTriggers.Update -> emit(DataState.Success(interactor.getCoursesLocal()))
            }
        }
    }
        .distinctUntilChanged()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            DataState.Loading
        )


    fun sortCoursesByDate(isSorted: Boolean) {
        viewModelScope.launch {
            refreshListener.emit(StateTriggers.Loading)
            interactor.setCoursesSorted(isSorted)
            delay(500)
            refreshListener.emit(StateTriggers.Update)
        }
    }


    fun addCourseToFavorite(model: CourseModel) {
        viewModelScope.launch {
            interactor.addOrRemoveCourseFavourite(model)
                .withProgressLoading()
                .collectLatest {
                    refreshListener.emit(StateTriggers.Update)
                }
        }

    }
}

sealed class StateTriggers {
    data object Update : StateTriggers()
    data object Loading : StateTriggers()
}
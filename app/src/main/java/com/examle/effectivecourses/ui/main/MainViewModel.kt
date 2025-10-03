package com.examle.effectivecourses.ui.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.examle.data.data.AppData
import com.examle.effectivecourses.ui.base.BaseViewModel
import io.reactivex.Completable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel() : BaseViewModel() {

    private val _isSplashShown = MutableStateFlow<Boolean>(true)
    val isSplashShown: StateFlow<Boolean> = _isSplashShown.asStateFlow()

    init {
        viewModelScope.launch {
            delay(1000)
            _isSplashShown.value = false
        }
    }
}
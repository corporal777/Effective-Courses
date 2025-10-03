package com.examle.effectivecourses.ui.base

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

abstract class BaseViewModel : ViewModel() {

    private val _buttonLoading = MutableStateFlow<Boolean>(false)
    val buttonLoading: StateFlow<Boolean> = _buttonLoading

    private val _loading = MutableStateFlow<Boolean>(false)
    val loading: StateFlow<Boolean> = _loading


    fun <T> Flow<T>.withButtonLoading(): Flow<T> {
        return this
            .onStart { _buttonLoading.value = true }
            .onCompletion { _buttonLoading.value = false }
    }

    fun <T> Flow<T>.withProgressLoading(): Flow<T> {
        return this
            .onStart { _loading.value = true }
            .onCompletion { _loading.value = false }
    }
}
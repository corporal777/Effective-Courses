package com.examle.effectivecourses.ui.base

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UIData {

    val _loading = MutableStateFlow<Boolean>(false)
    val loading: StateFlow<Boolean> = _loading
}
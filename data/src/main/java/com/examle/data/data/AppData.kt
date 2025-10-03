package com.examle.data.data

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.examle.domain.repository.AppPrefs

class AppData(private val appPrefs: AppPrefs) {

    var isLoggedIn: Boolean = appPrefs.isLoggedIn
        set(value) {
            if (field == value) return
            field = value
            appPrefs.isLoggedIn = value
        }
}
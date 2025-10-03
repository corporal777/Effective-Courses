package com.examle.data.repository

import android.content.Context
import com.examle.common.IS_LOGGED_IN
import com.examle.domain.repository.AppPrefs

class AppPrefsImpl(private val context: Context) : AppPrefs {
    private val prefs = context.getSharedPreferences("effective", Context.MODE_PRIVATE)

    override var isLoggedIn: Boolean
        get() = prefs.getBoolean(IS_LOGGED_IN, false)
        set(value) {
            prefs.edit().putBoolean(IS_LOGGED_IN, value).apply()
        }
}
package com.examle.effectivecourses.dataSource.data

import android.content.Context

class AppPrefsImpl(private val context: Context) : AppPrefs {
    private val prefs = context.getSharedPreferences("effective", Context.MODE_PRIVATE)

    override var isLoggedIn: Boolean
        get() = prefs.getBoolean(IS_LOGGED_IN, false)
        set(value) {
            prefs.edit().putBoolean(IS_LOGGED_IN, value).apply()
        }


    companion object {
        const val IS_LOGGED_IN = "logged_in"
    }
}
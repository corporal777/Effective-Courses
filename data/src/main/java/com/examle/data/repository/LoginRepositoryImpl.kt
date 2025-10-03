package com.examle.data.repository

import com.examle.data.data.AppData
import com.examle.domain.repository.LoginRepository

class LoginRepositoryImpl(private val appData: AppData) : LoginRepository {

    override suspend fun authProfile() {
        appData.isLoggedIn = true
    }


    override suspend fun logoutProfile() {
        appData.isLoggedIn = false
    }
}
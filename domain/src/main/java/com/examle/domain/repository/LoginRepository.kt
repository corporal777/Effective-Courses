package com.examle.domain.repository

interface LoginRepository {
    suspend fun authProfile()
    suspend fun logoutProfile()
}
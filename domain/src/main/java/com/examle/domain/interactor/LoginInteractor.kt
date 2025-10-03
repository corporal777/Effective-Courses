package com.examle.domain.interactor

import com.examle.domain.repository.LoginRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoginInteractor(private val repository: LoginRepository) {

    fun loginToProfile(): Flow<Unit> {
        return flow {
            repository.authProfile()
            delay(1000)
            emit(Unit)
        }
    }
}
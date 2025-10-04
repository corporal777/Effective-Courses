package com.examle.effectivecourses.ui.login

import androidx.lifecycle.viewModelScope
import com.examle.common.AuthUtils
import com.examle.domain.interactor.LoginInteractor
import com.examle.effectivecourses.ui.base.BaseViewModel
import com.examle.effectivecourses.ui.base.UIData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    uiData: UIData,
    private val interactor: LoginInteractor
) : BaseViewModel(uiData) {

    private val _buttonEnabled = MutableStateFlow(false)
    val buttonEnabled: StateFlow<Boolean> = _buttonEnabled

    private val _isLoginSuccess = MutableStateFlow(false)
    val isLoginSuccess: StateFlow<Boolean> = _isLoginSuccess

    private var email = ""
    private var password = ""


    fun loginProfile() {
        viewModelScope.launch {
            interactor.loginToProfile()
                .withButtonLoading()
                .catch { e -> e.printStackTrace() }
                .collect { _isLoginSuccess.update { true } }
        }
    }

    fun changeEmail(email: String) {
        this.email = email
        performDataChange()
    }

    fun changePassword(password: String) {
        this.password = password
        performDataChange()
    }

    private fun performDataChange() {
        _buttonEnabled.value = AuthUtils.isValidEmail(email) && password.isNotBlank()
    }

}
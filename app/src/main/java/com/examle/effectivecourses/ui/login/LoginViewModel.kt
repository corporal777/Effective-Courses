package com.examle.effectivecourses.ui.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.examle.effectivecourses.dataSource.data.AppData
import com.examle.effectivecourses.extensions.call
import com.examle.effectivecourses.extensions.performOnBackgroundOutOnMain
import com.examle.effectivecourses.extensions.withDelay
import com.examle.effectivecourses.ui.base.BaseViewModel
import com.examle.effectivecourses.utils.TextUtils
import io.reactivex.Completable
import io.reactivex.rxkotlin.subscribeBy

class LoginViewModel(private val appData: AppData) : BaseViewModel() {

    private val _buttonEnabled = mutableStateOf<Boolean>(false)
    val buttonEnabled: State<Boolean> = _buttonEnabled

    private val _loginSuccess = mutableStateOf<Boolean>(false)
    val loginSuccess: State<Boolean> = _loginSuccess

    var onLoginSuccess: () -> Unit = { }

    private var email = ""
    private var password = ""


    fun loginProfile() {
        Completable.fromAction { appData.isLoggedIn = true }
            .withDelay(1000)
            .performOnBackgroundOutOnMain()
            .withLoading()
            .subscribeBy { onLoginSuccess.invoke() }
            .call(compositeDisposable)
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
        _buttonEnabled.value = TextUtils.isValidEmail(email) && password.isNotBlank()
    }

}
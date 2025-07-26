package com.examle.effectivecourses.ui.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.examle.effectivecourses.extensions.withLoading
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    val compositeDisposable = CompositeDisposable()

    private val _loading = mutableStateOf<Boolean>(false)
    val loading: State<Boolean> = _loading

    private val _buttonLoading = mutableStateOf<Pair<Int, Boolean>>(Pair(0, false))
    val buttonLoading: State<Pair<Int, Boolean>> = _buttonLoading




    fun <T> Flowable<T>.withLoading() : Flowable<T> {
        return withLoading(_loading)
    }

    fun Completable.withLoading() : Completable {
        return withLoading(_loading)
    }

    fun <T> Maybe<T>.withButtonLoading(id: String): Maybe<T> {
        val loadingDisposable = Completable.complete()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete { _buttonLoading.value = Pair(id.toInt(), true) }
            .doOnDispose { _buttonLoading.value = Pair(id.toInt(), false) }
            .subscribe()
        val actionHide = Action {
            if (loadingDisposable.isDisposed) _buttonLoading.value = Pair(id.toInt(), false)
            else loadingDisposable.dispose()
        }

        fun <T> actionConsumer() = Consumer<T> {
            if (loadingDisposable.isDisposed) _buttonLoading.value = Pair(id.toInt(), false)
            else loadingDisposable.dispose()
        }
        return this.doOnDispose(actionHide)
            .doOnSuccess(actionConsumer())
            .doOnError(actionConsumer())
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
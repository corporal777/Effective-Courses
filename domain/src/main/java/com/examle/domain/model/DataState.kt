package com.examle.domain.model

sealed class DataState<out T> {
    data object Error : DataState<Nothing>()
    data object Loading : DataState<Nothing>()
    data class Success<out T>(val data: T) : DataState<T>()

}
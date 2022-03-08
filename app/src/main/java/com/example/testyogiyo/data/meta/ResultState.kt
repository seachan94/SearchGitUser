package com.example.testyogiyo.data.meta

sealed class ResultState<out T> {
    object Loading : ResultState<Nothing>()
    class Success<out T>(val data: T?) : ResultState<T>()
    class Error<T>(val message: String? = null , error : Throwable) : ResultState<T>()
}
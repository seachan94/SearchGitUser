package com.example.testyogiyo.data

sealed class NetworkStatus<out T> {
    object Loading : NetworkStatus<Nothing>()
    data class Success<out T>(val data: T?) : NetworkStatus<T>()
    data class Error<T>(val message: String) : NetworkStatus<T>()
}
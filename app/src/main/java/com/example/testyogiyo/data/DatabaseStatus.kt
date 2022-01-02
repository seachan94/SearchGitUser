package com.example.testyogiyo.data

sealed class DatabaseStatus <out T> {
    object Loading : DatabaseStatus<Nothing>()
    data class Success<out T>(val data: T?) : DatabaseStatus<T>()
    data class Error<T>(val message: String) : DatabaseStatus<T>()
}
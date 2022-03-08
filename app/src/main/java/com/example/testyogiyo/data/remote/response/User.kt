package com.example.testyogiyo.data.remote.response

data class User(
    val avatar_url : String,
    val login : String,
    val isLike : Boolean = false
)

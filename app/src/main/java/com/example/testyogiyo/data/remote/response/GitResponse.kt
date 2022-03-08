package com.example.testyogiyo.data.remote.response

data class GitResponse(
    val incomplete_results: Boolean,
    val items: List<User>,
    val total_count: Int
)

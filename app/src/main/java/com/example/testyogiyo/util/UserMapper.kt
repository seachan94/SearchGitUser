package com.example.testyogiyo.util

import com.example.testyogiyo.data.GitResponse
import com.example.testyogiyo.data.UserInfo
import com.example.testyogiyo.data.Users

object UserMapper {

    fun mapperToBook(response: GitResponse): Users {
        return Users(
            total_count = response.total_count,
            user = response.items.map {
                UserInfo(
                    it.avatar_url,
                    it.login,
                )
            } as ArrayList<UserInfo>
        )

    }
}
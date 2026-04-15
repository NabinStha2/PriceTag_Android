package com.example.price_tag.data.mapper.auth

import com.example.price_tag.data.model.auth.LoginResponse
import com.example.price_tag.domain.model.auth.User

fun LoginResponse.toDomain(): User {
    return User(
        accessToken = data?.accessToken ?: "",
        refreshToken = data?.refreshToken ?: ""
    )
}

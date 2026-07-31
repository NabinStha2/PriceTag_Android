package com.example.price_tag.feature.auth.data.mapper

import com.example.price_tag.feature.auth.data.model.LoginResponse
import com.example.price_tag.feature.auth.domain.model.User

fun LoginResponse.toDomain(): User {
    return User(
        accessToken = data?.accessToken ?: "",
        refreshToken = data?.refreshToken ?: ""
    )
}

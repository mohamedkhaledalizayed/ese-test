package com.neqabty.valify.modules.home.domain.entity

data class TokenEntity(
    val accessToken: String,
    val expiresIn: Int,
    val refreshToken: String,
    val scope: String,
    val tokenType: String
)

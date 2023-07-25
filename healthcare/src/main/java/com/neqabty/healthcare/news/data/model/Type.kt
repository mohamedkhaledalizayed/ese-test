package com.neqabty.healthcare.news.data.model


import androidx.annotation.Keep

@Keep
data class Type(
    val created_at: String,
    val id: Int,
    val name: String,
    val updated_at: String
)
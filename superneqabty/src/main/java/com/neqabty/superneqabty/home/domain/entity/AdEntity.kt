package com.neqabty.superneqabty.home.domain.entity

import com.google.gson.annotations.SerializedName

data class AdEntity(
    val createdAt: String = "",
    val entity: Int = 0,
    val id: Int = 0,
    val newsId: Int = 0,
    val image: String = "",
    val title: String = "",
    val type: String = "",
    val updatedAt: String = "",
    val url: String = ""
)

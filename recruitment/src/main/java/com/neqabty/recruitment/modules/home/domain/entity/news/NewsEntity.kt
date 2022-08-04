package com.neqabty.recruitment.modules.home.domain.entity.news



data class NewsEntity(
    val content: String,
    val id: Int,
    val imageFile: String,
    val title: String,
    val date: String
    )
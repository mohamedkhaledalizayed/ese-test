package com.neqabty.recruitment.modules.news.domain.entity



data class NewsEntity(
    val content: String,
    val id: Int,
    val imageFile: String,
    val title: String,
    val date: String
    )
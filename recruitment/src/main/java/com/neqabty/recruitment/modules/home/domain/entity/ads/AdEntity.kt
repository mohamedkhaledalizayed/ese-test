package com.neqabty.recruitment.modules.home.domain.entity.ads



data class AdEntity(
    val company: Company,
    val content: String,
    val id: Int,
    val imageFile: String,
    val title: String
)
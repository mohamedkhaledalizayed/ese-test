package com.neqabty.healthcare.modules.profile.domain.entity.profile



data class Package(
    val descriptionAr: String,
    val hint: String,
    val id: String,
    val insuranceCompanyId: Int,
    val nameAr: String,
    val recommended: Boolean,
    val serviceCode: String,
    val shortDescription: String,
    val updatedAt: String
)
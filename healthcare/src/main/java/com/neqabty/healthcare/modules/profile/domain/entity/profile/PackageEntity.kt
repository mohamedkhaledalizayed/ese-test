package com.neqabty.healthcare.modules.profile.domain.entity.profile



data class PackageEntity(
    val descriptionAr: String,
    val followers: List<FollowerEntity>,
    val hint: String,
    val id: String,
    val nameAr: String,
    val shortDescription: String,
    val createdAt: String
)
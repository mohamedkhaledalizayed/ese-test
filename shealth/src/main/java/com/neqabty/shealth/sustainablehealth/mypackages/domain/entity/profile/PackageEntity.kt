package com.neqabty.shealth.sustainablehealth.mypackages.domain.entity.profile


data class PackageEntity(
    val descriptionAr: String,
    val subscriberId: String,
    val followers: List<FollowerEntity>,
    val hint: String,
    val id: String,
    val nameAr: String,
    val shortDescription: String,
    val serviceCode: String,
    val serviceActionCode: String?,
    val packagePrice: String,
    val expiryDate: String?,
    val maxFollower: Int,
    val paid: Boolean,
    val createdAt: String
)
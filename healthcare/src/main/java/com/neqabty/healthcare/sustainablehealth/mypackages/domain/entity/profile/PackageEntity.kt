package com.neqabty.healthcare.sustainablehealth.mypackages.domain.entity.profile


data class PackageEntity(
    val descriptionAr: String,
    val subscriberId: String,
    val followers: List<FollowerEntity>,
    val hint: String,
    val id: String,
    val nameAr: String,
    val nameEn: String,
    val shortDescription: String,
    val prepaid: Boolean,
    val serviceCode: String,
    val serviceActionCode: String?,
    val packagePrice: String?,
    val vat: String?,
    val total: String?,
    val expiryDate: String?,
    val insuranceDocs: List<String>,
    val maxFollower: Int,
    val paid: Boolean,
    val createdAt: String
)
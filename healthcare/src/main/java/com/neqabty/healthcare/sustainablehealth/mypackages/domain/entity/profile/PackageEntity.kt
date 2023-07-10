package com.neqabty.healthcare.sustainablehealth.mypackages.domain.entity.profile

import com.neqabty.healthcare.commen.packages.packageslist.domain.entity.DetailEntity


data class PackageEntity(
    val descriptionAr: String,
    val subscriberId: String,
    val followers: List<FollowerEntity>,
    val details: List<DetailEntity>,
    val hint: String,
    val id: String,
    val name: String,
    val nameAr: String,
    val nameEn: String,
    val extension: String,
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
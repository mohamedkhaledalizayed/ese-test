package com.neqabty.healthcare.sustainablehealth.search.domain.entity.packages



data class PackagesEntity(
    val description: String,
    val extension: String,
    val details: List<DetailEntity>,
    val followerMultiRelation: Boolean,
    val hasFollower: Boolean,
    val hint: String?,
    val id: String,
    val maxFollower: Int,
    val name: String,
    val price: Double,
    val vat: Double,
    val total: Double,
    val recommended: Boolean,
    val serviceActionCode: String?,
    val serviceCode: String,
    val shortDescription: String?,
    val insuranceAmount: String?,
    val neddedInfo: String?,
    val targetGroups: String?
)
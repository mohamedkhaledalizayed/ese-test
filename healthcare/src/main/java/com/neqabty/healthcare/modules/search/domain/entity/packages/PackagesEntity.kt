package com.neqabty.healthcare.modules.search.domain.entity.packages



data class PackagesEntity(
    val description: String,
    val details: List<DetailEntity>,
    val followerMultiRelation: Boolean,
    val hasFollower: Boolean,
    val hint: String?,
    val id: String,
    val maxFollower: Int,
    val name: String,
    val price: Double,
    val recommended: Boolean,
    val serviceActionCode: String?,
    val serviceCode: String,
    val shortDescription: String?,
    val insuranceAmount: String?,
    val neddedInfo: String?,
    val targetGroups: String?
)
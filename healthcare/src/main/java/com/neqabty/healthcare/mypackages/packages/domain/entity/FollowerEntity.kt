package com.neqabty.healthcare.mypackages.packages.domain.entity



data class FollowerEntity(
    val fullName: String,
    val id: Int,
    val image: String,
    val nationalId: String,
    val qrCode: String,
    val relation: RelationEntity,
    val subscriberId: String,
    val relationType: Int
)
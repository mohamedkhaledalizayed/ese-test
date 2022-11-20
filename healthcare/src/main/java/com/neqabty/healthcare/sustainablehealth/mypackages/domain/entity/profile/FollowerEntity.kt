package com.neqabty.healthcare.sustainablehealth.mypackages.domain.entity.profile



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
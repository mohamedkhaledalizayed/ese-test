package com.neqabty.healthcare.modules.profile.domain.entity.profile



data class FollowerEntity(
    val fullName: String,
    val id: Int,
    val image: String,
    val nationalId: String,
    val qrCode: String,
    val relationType: Int,
    val subscriberId: String
    )
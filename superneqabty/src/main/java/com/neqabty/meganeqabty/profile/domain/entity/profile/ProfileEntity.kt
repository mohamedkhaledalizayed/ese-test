package com.neqabty.meganeqabty.profile.domain.entity.profile



data class ProfileEntity(
    val account: Account,
    val entity: Entity,
    val id: Int,
    val lastFeeYear: Int,
    val licenseEndDate: String,
    val membershipId: Int,
    val name: String,
    val nationalId: Long
)
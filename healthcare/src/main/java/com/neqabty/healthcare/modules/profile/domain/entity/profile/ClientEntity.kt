package com.neqabty.healthcare.modules.profile.domain.entity.profile



data class ClientEntity(
    val address: String,
    val email: String,
    val id: String,
    val job: String,
    val mobile: String,
    val name: String,
    val nationalId: String,
    val personalImage: String,
    val qrCode: String,
    val syndicateId: Int
)
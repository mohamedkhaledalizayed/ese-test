package com.neqabty.healthcare.commen.mypackages.packages.domain.entity



data class ClientEntity(
    val address: String,
    val userNumber: String?,
    val email: String,
    val id: String,
    val job: String,
    val mobile: String,
    val name: String,
    val nationalId: String?,
    val personalImage: String,
    val qrCode: String,
    val birthDate: String,
    val syndicateId: Int
)
package com.neqabty.meganeqabty.profile.domain.entity.licencestatus

data class LicenceStatusEntity(
    val syndicateName: String,
    val statusCode: Int,
    val statusMessage: String,
    val photo: String,
    val delivered: String
)
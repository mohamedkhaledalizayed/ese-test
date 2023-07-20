package com.neqabty.healthcare.profile.domain.entity.membershipcardstatus

data class CardStatusEntity(
    val syndicateName: String,
    val address: String,
    val mobile: String,
    val statusCode: Int,
    val statusMessage: String,
    val photo: String,
    val year: String,
    val delivered: String
)
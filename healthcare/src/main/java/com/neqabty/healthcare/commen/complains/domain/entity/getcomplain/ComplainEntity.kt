package com.neqabty.healthcare.commen.complains.domain.entity.getcomplain


data class ComplainEntity(
    val account: Int,
    val answer: String?,
    val createdAt: String,
    val email: String,
    val entity: Int,
    val id: Int,
    val message: String,
    val mobile: String,
    val updatedAt: String
)
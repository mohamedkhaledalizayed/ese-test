package com.neqabty.healthcare.complains.domain.entity


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
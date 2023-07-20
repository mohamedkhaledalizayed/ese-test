package com.neqabty.healthcare.payment.domain.entity.branches



data class BranchesEntity(
    val id: String,
    val address: String,
    val city: String,
    val entity: Entity?
)
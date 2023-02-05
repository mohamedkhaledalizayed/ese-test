package com.neqabty.healthcare.mega.payment.domain.entity.branches



data class BranchesEntity(
    val id: String,
    val address: String,
    val city: String,
    val entity: Entity?
)
package com.neqabty.healthcare.mega.payment.domain.entity.branches



data class BranchesEntity(
    val address: String,
    val city: String,
    val entity: Entity?
)
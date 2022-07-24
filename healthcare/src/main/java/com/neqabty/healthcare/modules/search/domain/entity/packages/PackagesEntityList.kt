package com.neqabty.healthcare.modules.search.domain.entity.packages



data class PackagesEntityList(
    val data: List<PackagesEntity>,
    val message: String,
    val status: Boolean
)
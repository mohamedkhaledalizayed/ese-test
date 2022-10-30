package com.neqabty.shealth.sustainablehealth.search.domain.entity.packages



data class PackagesEntityList(
    val data: List<PackagesEntity>,
    val message: String,
    val status: Boolean
)
package com.neqabty.healthcare.sustainablehealth.home.domain.entity.about.packages



data class PackagesEntityList(
    val data: List<PackagesEntity>,
    val message: String,
    val status: Boolean
)
package com.neqabty.healthcare.sustainablehealth.medicalnetwork.domain.entity.packages



data class PackagesEntityList(
    val data: List<PackagesEntity>,
    val message: String,
    val status: Boolean
)
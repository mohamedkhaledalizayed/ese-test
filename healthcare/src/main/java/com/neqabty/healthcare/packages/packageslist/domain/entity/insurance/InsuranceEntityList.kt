package com.neqabty.healthcare.packages.packageslist.domain.entity.insurance




data class InsuranceEntityList(
    val data: List<InsuranceEntity>,
    val message: String,
    val status: Boolean,
    val status_code: Int
)
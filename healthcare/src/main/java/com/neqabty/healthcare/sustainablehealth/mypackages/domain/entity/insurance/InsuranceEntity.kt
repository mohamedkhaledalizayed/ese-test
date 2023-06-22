package com.neqabty.healthcare.sustainablehealth.mypackages.domain.entity.insurance



data class InsuranceEntity(
    val data: List<String>,
    val message: String,
    val status: Boolean,
    val statusCode: Int
)
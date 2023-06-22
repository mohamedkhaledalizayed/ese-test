package com.neqabty.healthcare.sustainablehealth.subscribtions.domain.entity.terms



data class TermsEntityList(
    val data: List<TermsEntity>,
    val message: String,
    val status: Boolean,
    val status_code: Int
)
package com.neqabty.healthcare.sustainablehealth.subscribtions.domain.entity.terms



data class TermsEntity(
    val created_at: String,
    val id: Int,
    val notes: String?,
    val package_id: String,
    val terms_document: String,
    val updated_at: String
)
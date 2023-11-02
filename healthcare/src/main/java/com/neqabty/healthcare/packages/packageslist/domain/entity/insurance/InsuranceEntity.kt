package com.neqabty.healthcare.packages.packageslist.domain.entity.insurance




data class InsuranceEntity(
    val created_at: String,
    val id: Int,
    val name: String,
    val package_id: String,
    val terms_document: String,
    val updated_at: String
)
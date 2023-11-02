package com.neqabty.healthcare.packages.packageslist.data.model.insurance


import androidx.annotation.Keep

@Keep
data class InsuranceModel(
    val created_at: String?,
    val id: Int,
    val notes: String?,
    val package_id: String,
    val terms_document: String?,
    val updated_at: String?
)
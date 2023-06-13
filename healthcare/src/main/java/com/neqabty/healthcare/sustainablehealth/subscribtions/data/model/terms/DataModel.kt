package com.neqabty.healthcare.sustainablehealth.subscribtions.data.model.terms


import androidx.annotation.Keep

@Keep
data class DataModel(
    val created_at: String,
    val id: Int,
    val notes: String?,
    val package_id: String,
    val terms_document: String,
    val updated_at: String
)
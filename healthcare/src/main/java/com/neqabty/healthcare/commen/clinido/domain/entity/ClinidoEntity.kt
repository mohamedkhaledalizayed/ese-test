package com.neqabty.healthcare.commen.clinido.domain.entity

data class ClinidoEntity(
    val url: String,
    val message: String,
    val status: Boolean,
    val status_code: Int
)
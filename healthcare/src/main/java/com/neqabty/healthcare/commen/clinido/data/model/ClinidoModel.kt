package com.neqabty.healthcare.commen.clinido.data.model

data class ClinidoModel(
    val `data`: Data?,
    val message: String,
    val status: Boolean,
    val status_code: Int
)
package com.neqabty.healthcare.sustainablehealth.mypackages.data.model.insurance


import androidx.annotation.Keep

@Keep
data class InsuranceModel(
    val `data`: List<String>,
    val message: String,
    val status: Boolean,
    val status_code: Int
)
package com.neqabty.healthcare.packages.packageslist.data.model.insurance


import androidx.annotation.Keep

@Keep
data class InsuranceModelList(
    val `data`: List<InsuranceModel>,
    val message: String,
    val status: Boolean,
    val status_code: Int
)
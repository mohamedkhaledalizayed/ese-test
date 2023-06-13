package com.neqabty.healthcare.sustainablehealth.subscribtions.data.model.terms


import androidx.annotation.Keep

@Keep
data class TermsModel(
    val `data`: List<DataModel>,
    val message: String,
    val status: Boolean,
    val status_code: Int
)
package com.neqabty.healthcare.modules.suggestions.data.model

import androidx.annotation.Keep

@Keep
data class ComplaintBody(
    val catogory: Int,
    val details: String,
    val documents: List<String>?,
    val name: String,
    val phone: String
)
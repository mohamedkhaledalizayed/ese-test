package com.neqabty.healthcare.pharmacymart.home.data.model


import androidx.annotation.Keep

@Keep
data class RegisterModel(
    val `data`: Data,
    val message: String,
    val status: Boolean,
    val status_code: Int
)
package com.neqabty.healthcare.pharmacymart.address.data.model.addaddress


import androidx.annotation.Keep

@Keep
data class AddAddressModel(
    val `data`: Data,
    val message: String,
    val status: Boolean,
    val status_code: Int
)
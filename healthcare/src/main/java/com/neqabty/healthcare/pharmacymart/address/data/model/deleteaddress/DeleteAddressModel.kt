package com.neqabty.healthcare.pharmacymart.address.data.model.deleteaddress


import androidx.annotation.Keep

@Keep
data class DeleteAddressModel(
    val `data`: Any,
    val message: String,
    val status: Boolean,
    val status_code: Int
)
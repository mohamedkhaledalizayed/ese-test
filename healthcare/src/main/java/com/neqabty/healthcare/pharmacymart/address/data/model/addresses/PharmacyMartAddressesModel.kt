package com.neqabty.healthcare.pharmacymart.address.data.model.addresses


import androidx.annotation.Keep

@Keep
data class PharmacyMartAddressesModel(
    val data: AddressItemModel,
    val message: String,
    val status: Boolean,
    val status_code: Int
)
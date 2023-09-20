package com.neqabty.healthcare.pharmacymart.address.domain.entity

data class PharmacyMartAddressesListEntity(
    val data: List<PharmacyMartAddressEntity>,
    val message: String,
    val status: Boolean,
    val status_code: Int
)
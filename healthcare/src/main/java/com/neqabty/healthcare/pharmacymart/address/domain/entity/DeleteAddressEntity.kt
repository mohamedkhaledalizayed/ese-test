package com.neqabty.healthcare.pharmacymart.address.domain.entity


data class DeleteAddressEntity(
    val status: Boolean = false,
    val statusCode: Int = 0,
    val message: String = ""
)
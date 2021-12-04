package com.neqabty.yodawy.modules.address.domain.params


data class EditAddressUseCaseParams(
    val address_id: String,
    val mobile: String,
    val AddressAlias: String,
    val Address: String,
    val Floor: String,
    val Building: String,
    val Apartment: String,
    val long: Double,
    val lat: Double,
    val Landmark: String
    )

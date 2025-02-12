package com.neqabty.yodawy.modules.address.domain.params


data class AddAddressUseCaseParams(
    val mobile: String,
    val addressAlias: String,
    val address: String,
    val floor: String,
    val building: String,
    val apartment: String,
    val lat: String,
    val long: String,
    val landmark: String
)

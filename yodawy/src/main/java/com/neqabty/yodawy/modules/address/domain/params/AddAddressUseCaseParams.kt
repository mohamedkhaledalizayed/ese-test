package com.neqabty.yodawy.modules.address.domain.params

import com.google.gson.annotations.SerializedName

data class AddAddressUseCaseParams(
    val mobile: String,
    val addressAlias: String,
    val address: String,
    val floor: String,
    val building: String,
    val apartment: String,
    val landmark: String
)

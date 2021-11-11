package com.neqabty.yodawy.modules.address.domain.entity

data class UserEntity(
    val addresses: List<AddressEntity>,
    val neqabtyAddress: String,
    val plan: String,
    val yodawyId: String
)
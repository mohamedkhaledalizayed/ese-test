package com.neqabty.shealth.auth.login.domain.entity



data class User(
    val membershipId: String,
    val account: AccountEntity
)
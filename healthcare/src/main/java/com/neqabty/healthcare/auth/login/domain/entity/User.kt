package com.neqabty.healthcare.auth.login.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class User(
    val membershipId: String,
    val account: AccountEntity
): Parcelable
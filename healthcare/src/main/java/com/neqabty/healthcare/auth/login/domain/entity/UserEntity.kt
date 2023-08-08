package com.neqabty.healthcare.auth.login.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class UserEntity(
    val token: String,
    val user: User
): Parcelable
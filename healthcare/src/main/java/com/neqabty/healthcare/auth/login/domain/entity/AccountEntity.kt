package com.neqabty.healthcare.auth.login.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AccountEntity(
    val email: String,
    val entity: Entity,
    val fullName: String?,
    val id: Int,
    val image: String?,
    val mobile: String,
    val verifiedAccount: Boolean,
    val nationalId: String?
): Parcelable
package com.neqabty.healthcare.commen.profile.domain.entity.profile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Data(
    val email: String?,
    val entity: Entity,
    val fullName: String?,
    val id: Int,
    val image: String?,
    val mobile: String,
    val address: String?,
    val membershipId: Int,
    val nationalId: Long
): Parcelable
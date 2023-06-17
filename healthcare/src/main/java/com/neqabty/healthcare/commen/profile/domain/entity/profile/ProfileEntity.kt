package com.neqabty.healthcare.commen.profile.domain.entity.profile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class ProfileEntity(
    val data: Data,
    val message: String,
    val status: Int
): Parcelable
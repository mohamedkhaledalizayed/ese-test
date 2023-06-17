package com.neqabty.healthcare.commen.profile.domain.entity.profile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Entity(
    val code: String,
    val id: Int,
    val type: String,
    val name: String
): Parcelable
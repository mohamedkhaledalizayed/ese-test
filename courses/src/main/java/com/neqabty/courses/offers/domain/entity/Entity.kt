package com.neqabty.courses.offers.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Entity(
    val code: String = "",
    val name: String = ""
) : Parcelable
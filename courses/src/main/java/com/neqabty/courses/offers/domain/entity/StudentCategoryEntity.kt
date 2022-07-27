package com.neqabty.courses.offers.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StudentCategoryEntity(
    val code: String = "",
    val createdAt: String = "",
    val name: String = "",
    val updatedAt: String = ""
) : Parcelable
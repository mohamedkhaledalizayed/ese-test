package com.neqabty.courses.offers.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StudentEntity(
    val createdAt: String = "",
    val department: String = "",
    val email: String = "",
    val graduateYear: String = "",
    val id: Int = 0,
    val identityCard: String = "",
    val mobile: String = "",
    val name: String = "",
    val nequabtyId: String = "",
    val stdCategory: String = "",
    val studentStatus: String = "",
    val university: String = "",
    val updatedAt: String = ""
) : Parcelable
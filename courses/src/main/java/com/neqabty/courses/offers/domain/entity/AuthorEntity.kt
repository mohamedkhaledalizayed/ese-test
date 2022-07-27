package com.neqabty.courses.offers.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AuthorEntity(
    val email: String = "",
    val entityCode: String = "",
    val firstName: String = "",
    val id: Int = 0,
    val image: String = "",
    val lastName: String = "",
    val mobile: String = ""
) : Parcelable
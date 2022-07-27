package com.neqabty.courses.offers.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PricingEntity(
    val id: Int = 0,
    val offer: Int = 0,
    val price: String = "",
    val serviceActionCode: String = "",
    val serviceCode: String = "",
    val studentCategoryEntity: StudentCategoryEntity = StudentCategoryEntity()
) : Parcelable
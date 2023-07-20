package com.neqabty.healthcare.onboarding.contact.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GovEntity(
    val governorateAr: String,
    val areas: List<AreaEntity>
): Parcelable{
    override fun toString(): String {
        return governorateAr
    }
}

@Parcelize
data class AreaEntity(
    val areaName: String
): Parcelable{
    override fun toString(): String {
        return areaName
    }
}
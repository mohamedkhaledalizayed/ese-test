package com.neqabty.healthcare.commen.onboarding.contact.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GovEntity(
    val governorateAr: String,
    val areas: List<AreaEntity>
): Parcelable

@Parcelize
data class AreaEntity(
    val areaName: String
): Parcelable
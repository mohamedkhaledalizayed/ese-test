package com.neqabty.courses.offers.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LabEntity(
    val entity: Entity = Entity(),
    val id: Int = 0,
    val image: String = "",
    val name: String = ""
) : Parcelable
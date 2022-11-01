package com.neqabty.chefaa.modules.orders.domain.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrderClientEntity(
    val id: String = "",
    val name: String = ""
) : Parcelable
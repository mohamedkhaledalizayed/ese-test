package com.neqabty.yodawy.modules.orders.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrderAddressEntity(
    val address: String,
    val addressName: String,
    val id: String
): Parcelable
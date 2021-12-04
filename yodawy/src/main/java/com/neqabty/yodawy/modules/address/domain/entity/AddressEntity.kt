package com.neqabty.yodawy.modules.address.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AddressEntity(
    val address: String,
    val addressName: String,
    val apt: String,
    val buildingNumber: String,
    val floor: String,
    val latitude: Double,
    val longitude: Double,
    val adressId: String,
    val signature: String,
    val landmark: String
): Parcelable
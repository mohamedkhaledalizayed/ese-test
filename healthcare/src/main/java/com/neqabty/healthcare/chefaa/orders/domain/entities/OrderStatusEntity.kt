package com.neqabty.healthcare.chefaa.orders.domain.entities
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrderStatusEntity(
    val id: Int = 0,
    val titleAr: String = "",
    val titleEn: String = ""
) : Parcelable
package com.neqabty.yodawy.modules.orders.domain.entity


import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PrescriptionImagesEntity(
    val prescriptionImages: List<String>
): Parcelable
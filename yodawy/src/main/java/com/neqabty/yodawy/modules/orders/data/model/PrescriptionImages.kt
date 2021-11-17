package com.neqabty.yodawy.modules.orders.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PrescriptionImages(
    @SerializedName("PrescriptionImages")
    val prescriptionImages: List<String>
): Parcelable
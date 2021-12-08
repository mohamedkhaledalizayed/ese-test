package com.neqabty.yodawy.modules.orders.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Address(
    @SerializedName("Address")
    val address: String,
    @SerializedName("AddressName")
    val addressName: String,
    @SerializedName("Id")
    val id: String
): Parcelable
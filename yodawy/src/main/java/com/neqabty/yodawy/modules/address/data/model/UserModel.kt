package com.neqabty.yodawy.modules.address.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserModel(
    @SerializedName("addresses")
    val addresses: List<AddresseModel>,
    @SerializedName("neqabty_address")
    val neqabtyAddress: String,
    @SerializedName("plan")
    val plan: String,
    @SerializedName("YodawyId")
    val yodawyId: String
): Parcelable
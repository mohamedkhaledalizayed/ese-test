package com.neqabty.healthcare.commen.mypackages.packages.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class Wallet(
    @SerializedName("invitations")
    val invitations: Int,
    @SerializedName("total")
    val total: Int
)
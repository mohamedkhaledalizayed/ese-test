package com.neqabty.healthcare.modules.profile.data.model.profile


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class Wallet(
    @SerializedName("invitations")
    val invitations: Int,
    @SerializedName("total")
    val total: Int
)
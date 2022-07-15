package com.neqabty.healthcare.modules.profile.data.model.profile


import com.google.gson.annotations.SerializedName

data class Wallet(
    @SerializedName("invitations")
    val invitations: Int,
    @SerializedName("total")
    val total: Int
)
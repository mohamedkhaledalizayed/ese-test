package com.neqabty.healthcare.modules.profile.data.model.profile


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class Data(
    @SerializedName("client")
    val client: Client,
    @SerializedName("subscribed")
    val subscribed: List<Subscribed>,
    @SerializedName("wallet")
    val wallet: Wallet
)
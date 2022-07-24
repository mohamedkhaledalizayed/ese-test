package com.neqabty.healthcare.modules.profile.data.model.profile


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class Data(
    @SerializedName("client")
    val client: Client,
    @SerializedName("paid")
    val paid: Boolean,
    @SerializedName("subscribed_packages")
    val subscribedPackages: List<SubscribedPackage>,
    @SerializedName("wallet")
    val wallet: Wallet
)
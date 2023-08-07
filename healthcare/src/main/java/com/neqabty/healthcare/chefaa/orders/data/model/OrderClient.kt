package com.neqabty.healthcare.chefaa.orders.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class OrderClient(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("name")
    val name: String = ""
)
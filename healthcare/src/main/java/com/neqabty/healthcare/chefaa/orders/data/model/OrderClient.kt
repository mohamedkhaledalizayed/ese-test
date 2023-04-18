package com.neqabty.healthcare.chefaa.orders.data.model


import com.google.gson.annotations.SerializedName

data class OrderClient(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("name")
    val name: String = ""
)
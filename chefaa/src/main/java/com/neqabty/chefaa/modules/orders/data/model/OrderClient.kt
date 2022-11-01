package com.neqabty.chefaa.modules.orders.data.model


import com.google.gson.annotations.SerializedName

data class OrderClient(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("name")
    val name: String = ""
)
package com.neqabty.yodawy.modules.orders.data.model


import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("Address")
    val address: String,
    @SerializedName("AddressName")
    val addressName: String,
    @SerializedName("Id")
    val id: String
)
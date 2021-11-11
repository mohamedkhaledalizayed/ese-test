package com.neqabty.yodawy.modules.address.data.model


import com.google.gson.annotations.SerializedName

data class AddressResponse(
    @SerializedName("Id")
    val id: String,
    @SerializedName("IsSuccess")
    val isSuccess: Boolean,
    @SerializedName("Message")
    val message: String
)
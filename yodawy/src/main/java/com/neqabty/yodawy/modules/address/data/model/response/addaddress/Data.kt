package com.neqabty.yodawy.modules.address.data.model.response.addaddress


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("Id")
    val id: String,
    @SerializedName("IsSuccess")
    val isSuccess: Boolean,
    @SerializedName("Message")
    val message: String
)
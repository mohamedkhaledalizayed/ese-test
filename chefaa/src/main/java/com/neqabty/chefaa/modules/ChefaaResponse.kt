package com.neqabty.chefaa.modules


import com.google.gson.annotations.SerializedName

data class ChefaaResponse<T>(
    @SerializedName("data")
    val responseData: T?,
    @SerializedName("message")
    val messageAr: String = "",
    @SerializedName("status")
    val status: Boolean = false
)
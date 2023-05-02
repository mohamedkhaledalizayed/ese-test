package com.neqabty.healthcare.chefaa


import com.google.gson.annotations.SerializedName

data class ChefaaResponse<T>(
    @SerializedName("data")
    val responseData: T?,
    @SerializedName("message")
    val messageAr: String = "",
    @SerializedName("status_code")
    val statusCode: Int = 0,
    @SerializedName("status")
    val status: Boolean = false
)
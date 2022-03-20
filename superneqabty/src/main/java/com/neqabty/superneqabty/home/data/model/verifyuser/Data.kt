package com.neqabty.superneqabty.home.data.model.verifyuser


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("user_name")
    val userName: String,
    @SerializedName("user_number")
    val userNumber: String,
    @SerializedName("verified")
    val verified: Boolean
)
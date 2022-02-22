package com.neqabty.superneqabty.paymentdetails.data.model


import com.google.gson.annotations.SerializedName

data class Account(
    @SerializedName("email")
    val email: Any,
    @SerializedName("fullname")
    val fullname: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: Any,
    @SerializedName("mobile")
    val mobile: String,
    @SerializedName("national_id")
    val nationalId: String
)
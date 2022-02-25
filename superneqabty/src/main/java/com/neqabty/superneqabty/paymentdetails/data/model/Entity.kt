package com.neqabty.superneqabty.paymentdetails.data.model


import com.google.gson.annotations.SerializedName

data class Entity(
    @SerializedName("code")
    val code: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)
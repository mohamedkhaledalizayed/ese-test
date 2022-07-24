package com.neqabty.courses.offers.data.model


import com.google.gson.annotations.SerializedName

data class Entity(
    @SerializedName("code")
    val code: String = "",
    @SerializedName("name")
    val name: String = ""
)
package com.neqabty.courses.offers.data.model


import com.google.gson.annotations.SerializedName

data class StudentCategory(
    @SerializedName("code")
    val code: String = "",
    @SerializedName("created_at")
    val createdAt: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("updated_at")
    val updatedAt: String = ""
)
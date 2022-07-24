package com.neqabty.courses.offers.data.model


import com.google.gson.annotations.SerializedName

data class Author(
    @SerializedName("email")
    val email: String? = "",
    @SerializedName("entity_code")
    val entityCode: String = "",
    @SerializedName("first_name")
    val firstName: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("image")
    val image: String? = "",
    @SerializedName("last_name")
    val lastName: String = "",
    @SerializedName("mobile")
    val mobile: String = ""
)
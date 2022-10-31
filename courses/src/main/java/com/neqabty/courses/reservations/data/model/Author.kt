package com.neqabty.courses.reservations.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Author(
    @SerializedName("email")
    val email: Any,
    @SerializedName("entity_code")
    val entityCode: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: Any,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("mobile")
    val mobile: String
)
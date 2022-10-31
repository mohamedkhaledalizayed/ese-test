package com.neqabty.courses.reservations.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Entity(
    @SerializedName("code")
    val code: String,
    @SerializedName("name")
    val name: String
)
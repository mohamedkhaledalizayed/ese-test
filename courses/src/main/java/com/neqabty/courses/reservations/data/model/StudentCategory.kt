package com.neqabty.courses.reservations.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class StudentCategory(
    @SerializedName("code")
    val code: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("updated_at")
    val updatedAt: String
)
package com.neqabty.healthcare.medicalnetwork.data.model.filter


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class Degree(
    @SerializedName("created_at")
    val createdAt: Any,
    @SerializedName("degree_name")
    val degreeName: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("updated_at")
    val updatedAt: Any
)
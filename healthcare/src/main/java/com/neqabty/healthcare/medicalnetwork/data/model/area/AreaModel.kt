package com.neqabty.healthcare.medicalnetwork.data.model.area


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class AreaModel(
    @SerializedName("area_name")
    val areaName: String,
    @SerializedName("created_at")
    val createdAt: Any,
    @SerializedName("governorate_id")
    val governorateId: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("updated_at")
    val updatedAt: Any
)
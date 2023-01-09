package com.neqabty.healthcare.sustainablehealth.search.data.model.area


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

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
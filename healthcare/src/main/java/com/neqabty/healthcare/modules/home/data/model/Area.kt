package com.neqabty.healthcare.modules.home.data.model


import com.google.gson.annotations.SerializedName
import java.util.*

data class Area(
    @SerializedName("area_name")
    val areaName: String = "",
    @SerializedName("created_at")
    val createdAt: Date? = null ,
    @SerializedName("governorate_id")
    val governorateId: Int = 0,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("updated_at")
    val updatedAt: Date? = null
)
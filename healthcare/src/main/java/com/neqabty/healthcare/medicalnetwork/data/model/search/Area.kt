package com.neqabty.healthcare.medicalnetwork.data.model.search


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class Area(
    @SerializedName("area_name")
    val areaName: String?,
    @SerializedName("governorate_id")
    val governorateId: Int,
    @SerializedName("id")
    val id: Int
)
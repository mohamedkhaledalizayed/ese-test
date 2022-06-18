package com.neqabty.healthcare.modules.search.data.model.search


import com.google.gson.annotations.SerializedName

data class Area(
    @SerializedName("area_name")
    val areaName: String,
    @SerializedName("governorate_id")
    val governorateId: Int,
    @SerializedName("id")
    val id: Int
)
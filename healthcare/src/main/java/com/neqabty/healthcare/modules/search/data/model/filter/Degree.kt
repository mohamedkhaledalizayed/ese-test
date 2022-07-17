package com.neqabty.healthcare.modules.search.data.model.filter


import com.google.gson.annotations.SerializedName

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
package com.neqabty.healthcare.modules.search.data.model.filter


import com.google.gson.annotations.SerializedName

data class Profession(
    @SerializedName("created_at")
    val createdAt: Any,
    @SerializedName("id")
    val id: Int,
    @SerializedName("profession_name")
    val professionName: String,
    @SerializedName("updated_at")
    val updatedAt: Any
)
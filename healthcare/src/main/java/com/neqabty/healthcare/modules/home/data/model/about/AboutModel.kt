package com.neqabty.healthcare.modules.home.data.model.about


import com.google.gson.annotations.SerializedName

data class AboutModel(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("deleted_at")
    val deletedAt: Any,
    @SerializedName("id")
    val id: Int,
    @SerializedName("key")
    val key: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("value")
    val value: String
)
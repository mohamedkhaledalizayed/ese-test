package com.neqabty.healthcare.modules.search.data.model.packages


import com.google.gson.annotations.SerializedName

data class DetailModel(
    @SerializedName("card_id")
    val cardId: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("deleted_at")
    val deletedAt: Any,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("updated_at")
    val updatedAt: String
)
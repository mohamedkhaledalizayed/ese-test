package com.neqabty.meganeqabty.syndicates.data.model


import com.google.gson.annotations.SerializedName

data class Type(
    @SerializedName("created_at")
    val createdAt: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("updated_at")
    val updatedAt: String = ""
)
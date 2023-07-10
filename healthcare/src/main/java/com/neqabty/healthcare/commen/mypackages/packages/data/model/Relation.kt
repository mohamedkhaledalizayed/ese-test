package com.neqabty.healthcare.commen.mypackages.packages.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class Relation(
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("relation")
    val relation: String,
    @SerializedName("updated_at")
    val updatedAt: String?
)
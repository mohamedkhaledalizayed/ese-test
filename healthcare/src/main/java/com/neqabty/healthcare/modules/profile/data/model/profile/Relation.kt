package com.neqabty.healthcare.modules.profile.data.model.profile


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
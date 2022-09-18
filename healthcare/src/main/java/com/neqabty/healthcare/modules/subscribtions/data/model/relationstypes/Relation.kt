package com.neqabty.healthcare.modules.subscribtions.data.model.relationstypes


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class Relation(
    @SerializedName("created_at")
    val createdAt: Any,
    @SerializedName("id")
    val id: Int,
    @SerializedName("relation")
    val relation: String,
    @SerializedName("updated_at")
    val updatedAt: Any
)
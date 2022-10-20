package com.neqabty.healthcare.sustainablehealth.search.data.model.filter


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
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
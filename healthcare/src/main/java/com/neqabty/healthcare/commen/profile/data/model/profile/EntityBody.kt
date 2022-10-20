package com.neqabty.healthcare.commen.profile.data.model.profile


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class EntityBody(
    @SerializedName("code")
    val code: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("type")
    val type: String
)
package com.neqabty.recruitment.modules.profile.data.model.addengineerlanguage


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Engineerlanguage(
    @SerializedName("engineer")
    val engineer: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("language")
    val language: Int,
    @SerializedName("level")
    val level: String
)
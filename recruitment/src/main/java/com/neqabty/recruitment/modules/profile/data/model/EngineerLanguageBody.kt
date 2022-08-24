package com.neqabty.recruitment.modules.profile.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class EngineerLanguageBody(
    @SerializedName("engineerlanguage")
    val engineerLanguage: EngineerLanguage
)

@Keep
data class EngineerLanguage(
    @SerializedName("engineer")
    val engineer: Int,
    @SerializedName("language")
    val language: Int,
    @SerializedName("level")
    val level: String
)
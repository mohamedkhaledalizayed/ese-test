package com.neqabty.recruitment.modules.profile.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class LanguageBody(
    @SerializedName("language")
    val language: Language
)

@Keep
data class Language(
    @SerializedName("name")
    val name: String
)
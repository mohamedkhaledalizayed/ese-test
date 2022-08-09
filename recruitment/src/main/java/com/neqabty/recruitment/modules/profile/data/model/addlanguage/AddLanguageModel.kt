package com.neqabty.recruitment.modules.profile.data.model.addlanguage


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class AddLanguageModel(
    @SerializedName("language")
    val language: Language
)
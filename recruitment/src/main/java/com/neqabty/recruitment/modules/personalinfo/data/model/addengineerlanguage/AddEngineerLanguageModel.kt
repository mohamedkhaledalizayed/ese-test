package com.neqabty.recruitment.modules.personalinfo.data.model.addengineerlanguage


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class AddEngineerLanguageModel(
    @SerializedName("engineerlanguage")
    val engineerlanguage: Engineerlanguage
)
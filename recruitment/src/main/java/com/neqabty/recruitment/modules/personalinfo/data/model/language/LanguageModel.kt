package com.neqabty.recruitment.modules.personalinfo.data.model.language


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class LanguageModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)
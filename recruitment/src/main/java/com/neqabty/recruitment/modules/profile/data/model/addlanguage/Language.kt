package com.neqabty.recruitment.modules.profile.data.model.addlanguage


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Language(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)
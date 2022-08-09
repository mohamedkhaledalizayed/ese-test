package com.neqabty.recruitment.modules.profile.data.model.industries


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class IndustryModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)
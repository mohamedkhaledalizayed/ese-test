package com.neqabty.recruitment.modules.personalinfo.data.model.country


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class CountryModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)
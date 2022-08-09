package com.neqabty.recruitment.modules.profile.data.model.engineerdata


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class MaritalStatusModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)